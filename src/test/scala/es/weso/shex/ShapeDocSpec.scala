package es.weso.shex

import es.weso.shex.ShapeSyntax._
import es.weso.shex.ShapeDoc._
import es.weso.rdfgraph.nodes._
import es.weso.rdfgraph._
import org.scalatest._
import org.scalatest.prop._
import es.weso.rdf.PrefixMap
import es.weso.shacl.PREFIXES._
import es.weso.shacl.PrefixMaps

class ShapeDocSpec
    extends FunSpec
    with Matchers
    with Checkers {

  describe("Shape Doc") {
    it("Should be able to define basic mbox") {
      val termFoafmbox = NameTerm(t = add(foaf, "mbox"))
      val arcRule = ArcRule(n = termFoafmbox,
        v = typeShIRI,
        id = None)
      val shape = Shape(IRILabel(IRI("test")), arcRule)
      info("Shape: " + ShapeDoc.shape2String(shape)(PrefixMap.empty))
    }

    it("Should be able to define basic UserShapes") {
      val termFoaf_mbox = NameTerm(t = add(foaf, "mbox"))
      val termFoaf_name = NameTerm(t = add(foaf, "name"))
      val termFoaf_givenName = NameTerm(t = add(foaf, "givenName"))
      val termFoaf_familyName = NameTerm(t = add(foaf, "familyName"))

      val nameRule = ArcRule(n = termFoaf_name,
        v = typeXsdString,
        id = None)

      val givenNameRule = ArcRule(n = termFoaf_givenName,
        v = typeXsdString,
        id = None)

      val familyNameRule = ArcRule(n = termFoaf_familyName,
        v = typeXsdString,
        id = None)

      val givenName_and_familyName = AndRule(r1 = givenNameRule, r2 = familyNameRule)

      val or_names = OrRule(r1 = nameRule, r2 = givenName_and_familyName)

      val mboxRule = ArcRule(n = termFoaf_mbox,
        v = typeShIRI,
        id = None)

      val shape = Shape(IRILabel(IRI("test")), AndRule(or_names, mboxRule))
      val pm = PrefixMaps.commonShacl
      val schema = Schema(pm = pm, shEx = ShEx(rules = Seq(shape), start = None))
      info("Schema: " + schema.toString)
    }

  }

}