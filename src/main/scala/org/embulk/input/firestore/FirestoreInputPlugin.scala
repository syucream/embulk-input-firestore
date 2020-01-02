package org.embulk.input.firestore

import java.io.FileInputStream
import java.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.Firestore
import com.google.firebase.cloud.FirestoreClient
import com.google.firebase.{FirebaseApp, FirebaseOptions}
import com.syucream.firesql.FireSQL
import org.embulk.config.{ConfigDiff, ConfigSource, TaskReport, TaskSource}
import org.embulk.spi.`type`.Types
import org.embulk.spi.PageBuilder
import org.embulk.spi.json.JsonParser
import org.embulk.spi.{Exec, InputPlugin, PageOutput, Schema}

import scala.util.Try

case class FirestoreInputPlugin() extends InputPlugin {
  private val TASK_COUNT = 1

  private val jsonParser = new JsonParser()
  private val objectMapper = new ObjectMapper()

  override def transaction(
      config: ConfigSource,
      control: InputPlugin.Control
  ): ConfigDiff = {
    val task = config.loadConfig(classOf[PluginTask])

    // TODO runtime validations

    val schema = Schema
      .builder()
      .add(task.getJsonColumnName, Types.JSON)
      .build()

    resume(task.dump(), schema, TASK_COUNT, control)
  }

  override def resume(
      taskSource: TaskSource,
      schema: Schema,
      taskCount: Int,
      control: InputPlugin.Control
  ): ConfigDiff = {
    // XXX unimplemented
    control.run(taskSource, schema, taskCount)
    Exec.newConfigDiff()
  }

  override def cleanup(
      taskSource: TaskSource,
      schema: Schema,
      taskCount: Int,
      successTaskReports: util.List[TaskReport]
  ): Unit = {
    // nothing to do
  }

  override def run(
      taskSource: TaskSource,
      schema: Schema,
      taskIndex: Int,
      output: PageOutput
  ): TaskReport = {
    val task = taskSource.loadTask(classOf[PluginTask])
    val allocator = task.getBufferAllocator
    val pageBuilder = new PageBuilder(allocator, schema, output)

    val firestore = createFirestore(task.getJsonKeyfile).get
    val firesql = new FireSQL(firestore)
    val query = firesql.query(task.getSql)

    val col = pageBuilder.getSchema.getColumn(0)

    query.get.getDocuments.forEach { d =>
      val json = objectMapper.writeValueAsString(d.getData)
      pageBuilder.setJson(col, jsonParser.parse(json))
      pageBuilder.addRecord()
    }
    pageBuilder.finish()

    Exec.newTaskReport()
  }

  override def guess(config: ConfigSource): ConfigDiff =
    Exec.newConfigDiff()

  private def createFirestore(pathToCredJson: String): Try[Firestore] = {
    Try {
      val serviceAccount = new FileInputStream(pathToCredJson)
      val credentials = GoogleCredentials.fromStream(serviceAccount)
      val options =
        new FirebaseOptions.Builder().setCredentials(credentials).build
      FirebaseApp.initializeApp(options)

      FirestoreClient.getFirestore
    }
  }
}
