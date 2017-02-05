package info.hupel.isabelle.cli

import scala.concurrent._

import monix.execution.{Cancelable, CancelableFuture}

import org.log4s._

import info.hupel.isabelle.api._
import info.hupel.isabelle.setup.Setup

/**
 * Simple bundle of an Isabelle [[info.hupel.isabelle.setup.Setup setup]], a
 * matching [[info.hupel.isabelle.api.Environment environment]] and a requested
 * [[info.hupel.isabelle.api.Configuration configuration]].
 */
final case class Bundle(env: Environment, setup: Setup, configuration: Configuration)

trait Command {

  protected lazy val logger = getLogger(getClass)

  def run(bundle: Bundle, args: List[String])(implicit ec: ExecutionContext): Future[Unit] =
    cancelableRun(bundle, args)

  def cancelableRun(bundle: Bundle, args: List[String])(implicit ec: ExecutionContext): CancelableFuture[Unit] =
    CancelableFuture(run(bundle, args), Cancelable.empty)

}
