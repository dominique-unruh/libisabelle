package edu.tum.cs.isabelle.impl

import java.nio.file.Path

import scala.concurrent.ExecutionContext

import edu.tum.cs.isabelle.api

@api.Implementation(identifier = "2014")
final class Environment(home: Path) extends api.Environment(home) {

  isabelle.Isabelle_System.init(
    isabelle_home = home.toAbsolutePath.toString,
    cygwin_root = home.resolve("contrib/cygwin").toAbsolutePath.toString
  )

  type XMLTree = isabelle.XML.Tree

  def fromYXML(source: String) = isabelle.YXML.parse(source)
  def toYXML(tree: XMLTree) = isabelle.YXML.string_of_tree(tree)

  def text(content: String) = isabelle.XML.Text(content)
  def elem(markup: api.Markup, body: XMLBody) = isabelle.XML.Elem(isabelle.Markup(markup._1, markup._2), body)

  private def destMarkup(markup: isabelle.Markup) =
    (markup.name, markup.properties)

  def destTree(tree: XMLTree) = tree match {
    case isabelle.XML.Text(content) => Left(content)
    case isabelle.XML.Elem(markup, body) => Right((destMarkup(markup), body))
  }

  protected[isabelle] val exitTag = isabelle.Markup.EXIT
  protected[isabelle] val functionTag = isabelle.Markup.FUNCTION
  protected[isabelle] val initTag = isabelle.Markup.INIT
  protected[isabelle] val protocolTag = isabelle.Markup.PROTOCOL

  lazy val executionContext =
    isabelle.Future.execution_context

  protected[isabelle] type Session = isabelle.Session

  private lazy val options = isabelle.Options.init()

  private def mkPaths(path: Option[Path]) =
    path.map(p => isabelle.Path.explode(isabelle.Isabelle_System.posix_path(p.toAbsolutePath.toString))).toList


  protected[isabelle] def build(config: api.Configuration) =
    isabelle.Build.build(
      options = options,
      progress = new isabelle.Build.Console_Progress(verbose = true),
      build_heap = true,
      dirs = mkPaths(config.path),
      verbose = true,
      sessions = List(config.session)
    )

  protected[isabelle] def create(config: api.Configuration, consumer: (api.Markup, XMLBody) => Unit) = {
    val content = isabelle.Build.session_content(options, false, mkPaths(config.path), config.session)
    val resources = new isabelle.Resources(content.loaded_theories, content.known_theories, content.syntax)
    val session = new isabelle.Session(resources)

    session.all_messages += isabelle.Session.Consumer[isabelle.Prover.Message]("firehose") {
      case msg: isabelle.Prover.Protocol_Output =>
        consumer(destMarkup(msg.message.markup), isabelle.YXML.parse_body(msg.text))
      case msg: isabelle.Prover.Output =>
        consumer(destMarkup(msg.message.markup), msg.message.body)
      case _ =>
    }

    session.start("Isabelle" /* name is ignored anyway */, List("-r", "-q", config.session))
    session
  }

  protected[isabelle] def sendCommand(session: Session, name: String, args: List[String]) =
    session.protocol_command(name, args: _*)

  protected[isabelle] def sendOptions(session: Session) =
    session.protocol_command("Prover.options", isabelle.YXML.string_of_body(options.encode))

  protected[isabelle] def dispose(session: Session) = session.stop()

}
