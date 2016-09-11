package info.hupel.isabelle.japi

import java.nio.file.Path

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

import info.hupel.isabelle.api._
import info.hupel.isabelle.setup._

object JSetup {

  def defaultSetup(version: Version, timeout: Duration): Setup =
    Setup.defaultSetup(version).valueOr(reason => sys.error(reason.explain))

  def defaultSetup(version: Version): Setup =
    defaultSetup(version, Duration.Inf)

  def makeEnvironment(setup: Setup, timeout: Duration): Environment =
    Await.result(setup.makeEnvironment, timeout)

  def makeEnvironment(setup: Setup): Environment =
    makeEnvironment(setup, Duration.Inf)

}
