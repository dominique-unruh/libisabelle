package edu.tum.cs.isabelle.tests

import scala.concurrent._
import scala.concurrent.duration._
import scala.math.BigInt

import org.specs2.Specification
import org.specs2.specification.core.Env
import org.specs2.matcher.Matcher

import edu.tum.cs.isabelle._
import edu.tum.cs.isabelle.api._

class LibisabelleSpec(val specs2Env: Env) extends Specification with DefaultSetup { def is = s2"""

  Basic protocol interaction

  An Isabelle session
    can be started          ${system must exist.awaitFor(30.seconds)}
    can load theories       ${loaded must beRight(()).awaitFor(30.seconds)}
    reacts to requests      ${response must beRight("prop => prop => prop").awaitFor(5.seconds)}
    handles errors          ${error must beLeft.awaitFor(5.seconds)}
    can cancel requests     ${cancelled.failed must beAnInstanceOf[System.CancellationException].awaitFor(5.seconds)}
    can be torn down        ${teardown must exist.awaitFor(5.seconds)}"""


  implicit val ee = specs2Env.executionEnv

  val TypeOf = Operation.implicitly[String, String]("type_of")
  val Sleepy = Operation.implicitly[BigInt, Unit]("sleepy")

  val system = System.create(env)(config)
  val loaded = system.flatMap(_.invoke(Operation.UseThys)(List("tests/src/test/isabelle/Test")))
  val response = for { s <- system; _ <- loaded; res <- s.invoke(TypeOf)("op ==>") } yield res
  val error = for { s <- system; _ <- loaded; res <- s.invoke(TypeOf)("==>") } yield res

  val responses = Future.sequence(List(response, error))

  val cancelled =
    for {
      s <- system
      _ <- loaded
      _ <- responses
      res <- { val future = s.invoke(Sleepy)(1); s.cancelAll(); future }
    }
    yield ()

  val teardown =
    for {
      s <- system
      _ <- responses
      _ <- s.dispose
    }
    yield ()

  def exist[A]: Matcher[A] = ((a: A) => a != null, "doesn't exist")

}
