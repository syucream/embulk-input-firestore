package org.embulk.input.firestore

import java.util

import org.embulk.config.{ConfigDiff, ConfigSource, TaskReport, TaskSource}
import org.embulk.spi.{Exec, InputPlugin, PageOutput, Schema}

case class FirestoreInputPlugin() extends InputPlugin {

  override def transaction(config: ConfigSource, control: InputPlugin.Control): ConfigDiff = {
    null
  }

  override def resume(taskSource: TaskSource, schema: Schema, taskCount: Int, control: InputPlugin.Control): ConfigDiff = {
    null
  }

  override def cleanup(taskSource: TaskSource, schema: Schema, taskCount: Int, successTaskReports: util.List[TaskReport]): Unit = {
  }

  override def run(taskSource: TaskSource, schema: Schema, taskIndex: Int, output: PageOutput): TaskReport = {
    null
  }

  override def guess(config: ConfigSource): ConfigDiff =
    Exec.newConfigDiff()
}
