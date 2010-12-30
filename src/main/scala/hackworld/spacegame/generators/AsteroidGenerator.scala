package hackworld.spacegame.generators

import hackworld.spacegame.entity.Entity
import com.jme3.scene.Geometry
import com.jme3.scene.shape.Sphere
import com.jme3.material.Material
import com.jme3.math.ColorRGBA
import com.jme3.asset.AssetManager
import hackworld.spacegame.facets.{Location, Appearance}

/**
 * 
 */

class AsteroidGenerator extends Generator {
  def generate(assetManager: AssetManager): Entity = {

    val entity: Entity = new Entity()
    entity.addFacet(new Location())

    val shape = new Sphere(30, 30, 50, true, false)
    val geometry: Geometry = new Geometry("Asteroid", shape)
//    geometry.setLocalTranslation(0, 0, -1000)


    val mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md")
    mat.setBoolean("m_UseMaterialColors", true);
    mat.setColor("m_Ambient", ColorRGBA.DarkGray);
    mat.setColor("m_Diffuse", ColorRGBA.LightGray);
    mat.setColor("m_Specular", ColorRGBA.Cyan);
    mat.setFloat("m_Shininess", 6);

    //     val mat = new Material(assetManager, "Common/MatDefs/Misc/SolidColor.j3md")
    //   val mat = new Material(assetManager, "Common/MatDefs/Misc/SimpleTextured.j3md")
    //     val mat = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md")
    //     mat.setColor("m_Color", ColorRGBA.Blue)
//    mat.setTexture("m_DiffuseMap", assetManager.loadTexture("textures/purple.jpg"))
    mat.setTexture("m_DiffuseMap", assetManager.loadTexture("textures/clouds.jpg"))
    //    mat.setTexture("m_NormalMap", assetManager.loadTexture("textures/lava.jpg"))

    geometry.setMaterial(mat)

 //   entity.addFacet(new Appearance(geometry))

    entity
  }
}
