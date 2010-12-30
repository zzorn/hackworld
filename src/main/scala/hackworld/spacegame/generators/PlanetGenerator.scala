package hackworld.spacegame.generators

import hackworld.spacegame.entity.Entity
import com.jme3.scene.Geometry
import com.jme3.scene.shape.Sphere
import com.jme3.material.Material
import com.jme3.math.ColorRGBA
import com.jme3.asset.AssetManager
import scala.actors
import hackworld.spacegame.facets.{Location, Appearance}

/**
 *
 */
class PlanetGenerator
        extends Generator
{
  def generate(assetManager: AssetManager): Entity =
  {

    val planet: Entity = new Entity()
    planet.addFacet(new Location)

    val shape = new Sphere(40, 40, 500, true, false)
    val geometry: Geometry = new Geometry("Planet", shape)
    geometry.setLocalTranslation(0, 0, -1000)
//    planet.addFacet(new Appearance(geometry))



    val mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md")
    mat.setBoolean("m_UseMaterialColors", true);
    mat.setColor("m_Ambient", ColorRGBA.DarkGray);
    mat.setColor("m_Diffuse", ColorRGBA.White);
    mat.setColor("m_Specular", ColorRGBA.Pink);
    mat.setFloat("m_Shininess", 12);

    //     val mat = new Material(assetManager, "Common/MatDefs/Misc/SolidColor.j3md")
    //   val mat = new Material(assetManager, "Common/MatDefs/Misc/SimpleTextured.j3md")
    //     val mat = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md")
    //     mat.setColor("m_Color", ColorRGBA.Blue)
//    mat.setTexture("m_DiffuseMap", assetManager.loadTexture("textures/purple.jpg"))
    mat.setTexture("m_DiffuseMap", assetManager.loadTexture("textures/lava.jpg"))
    //    mat.setTexture("m_NormalMap", assetManager.loadTexture("textures/lava.jpg"))

    geometry.setMaterial(mat)

    planet.withFacet[Appearance] (a => a.geometry = geometry)

    planet
  }
}