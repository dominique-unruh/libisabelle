/*  Title:      Pure/ROOT.scala
    Module:     PIDE
    Author:     Makarius

Root of isabelle package.
*/

package object isabelle extends isabelle.Basic_Library
{
  object Distribution     /*filled-in by makedist*/
  {
    val version = "Isabelle2016: February 2016"
    val is_identified = true
    val is_official = true
  }
}

