package info.hupel.isabelle.examples.scala

import scala.concurrent._
import scala.concurrent.duration._

import monix.execution.Scheduler.Implicits.global

import info.hupel.isabelle._
import info.hupel.isabelle.api._
import info.hupel.isabelle.setup._

object Hello_PIDE extends App {

  val setup = Setup.default(Version.Stable("2017"), false).right.get // yolo
  val resources = Resources.dumpIsabelleResources().right.get // yolo
  val config = Configuration.simple("Protocol")

  val transaction =
    for {
      env <- setup.makeEnvironment(resources, Nil)
      sys <- System.create(env, config)
      response <- sys.invoke(Operation.Hello)("world")
      _ = println(response.unsafeGet)
      () <- sys.dispose
    } yield ()

  Await.result(transaction, Duration.Inf)

}
