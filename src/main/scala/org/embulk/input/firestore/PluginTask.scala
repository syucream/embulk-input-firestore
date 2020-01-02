package org.embulk.input.firestore

import org.embulk.config.{Config, ConfigDefault, ConfigInject, Task}
import org.embulk.spi.BufferAllocator

trait PluginTask extends Task {

  @Config("project_id")
  def getProjectId: String

  @Config("json_keyfile")
  def getJsonKeyfile: String

  @Config("json_column_name")
  @ConfigDefault("\"record\"")
  def getJsonColumnName: String

  @Config("sql")
  def getSql: String

  @ConfigInject
  def getBufferAllocator: BufferAllocator
}
