package hackworld.spacegame.facets

import com.jme3.math.{Vector3f, Quaternion}
import hackworld.spacegame.entity.Facet

/**
 * 
 */
class Location extends Facet {
  val pos = p('pos, new Vector3f()).onChange(() => updateGeometry())
  val dir = p('dir, new Quaternion()).onChange(() => updateGeometry())



  private def updateGeometry(): Unit = {
    withFacet[Appearance]{ a =>
      a.geometry.setLocalTranslation(pos())
      a.geometry.setLocalRotation(dir())
    }
  }
}
