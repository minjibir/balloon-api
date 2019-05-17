import io.getquill.{MysqlJdbcContext, SnakeCase}

package object DbConfig {
   implicit lazy val ctx: MysqlJdbcContext[SnakeCase] = new MysqlJdbcContext[SnakeCase](SnakeCase, "ctx")
 }
