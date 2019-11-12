package org.embulk.input.firestore

import java.util

import org.embulk.config.{ConfigDiff, ConfigSource, TaskReport, TaskSource}
import org.embulk.spi.{InputPlugin, PageOutput, Schema}

case class FirestoreInputPlugin() extends InputPlugin {
  override def transaction(config: ConfigSource, control: InputPlugin.Control): ConfigDiff = ???

  override def resume(taskSource: TaskSource, schema: Schema, taskCount: Int, control: InputPlugin.Control): ConfigDiff = ???

  override def cleanup(taskSource: TaskSource, schema: Schema, taskCount: Int, successTaskReports: util.List[TaskReport]): Unit = ???

  override def run(taskSource: TaskSource, schema: Schema, taskIndex: Int, output: PageOutput): TaskReport = ???

  override def guess(config: ConfigSource): ConfigDiff = ???
}
