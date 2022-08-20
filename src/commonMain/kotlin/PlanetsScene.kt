import com.soywiz.korge.input.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import com.soywiz.korim.font.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*

class PlanetsScene(val gs: GalaxyState) : Scene() {

    private val direction = mutableListOf<Boolean>()

    override suspend fun SContainer.sceneInit() {
        var startx = 200
        var starty = 600

        val font = resourcesVfs["fonts/Android.ttf"].readTtfFont()

        for((i, planet) in gs.stars[0]!!.planets.values.withIndex())
        {
            var fileName = when( planet.type) {
                PlanetType.TOXIC -> "planets/planet1.png"
                PlanetType.OCEAN -> "planets/planet2.png"
                PlanetType.TERRAN -> "planets/planet3.png"
                PlanetType.DESSERT -> "planets/planet4.png"
                PlanetType.VOLCANIC -> "planets/planet5.png"
                PlanetType.BARREN -> "planets/planet6.png"
                PlanetType.SUPERTERRAN -> "planets/planet7.png"
                PlanetType.TROPICAL -> "planets/planet10.png"
            }
            val planetImage = image(resourcesVfs[fileName].readBitmap()) {
                scale(0.5)
                position(startx, starty)
            }
            direction.add(i, false)
            planetImage.addUpdater { updatePlanet(planetImage,i) }
            planetImage.onClick { gs.activePlayerPlanet = i; sceneContainer.changeTo<PlanetScene>()  }

            text( planet.name, 50.00, Colors.CYAN, font)
            {
                centerXOn(planetImage)
                alignTopToTopOf(planetImage, 12.0)
            }

            //startx += 50
            starty -= 200
          }

        var fileName = when( gs.stars[0]!!.type) {
            StarType.YELLOW -> "stars/Star cK gK eg9.bmp"
            StarType.BLUE -> "stars/Star B supeg5.bmp"
            StarType.RED -> "stars/Star M supeg5.bmp"
        }
        val starImage = image(resourcesVfs[fileName].readBitmap()) {
            scale(0.5)
            position( width/2, 800.00)
        }
        text( gs.stars[0]!!.name, 50.00, Colors.CYAN, font)
        {
            centerXOn(starImage)
            alignTopToTopOf(starImage, 12.0)
        }


    }

    private fun updatePlanet(planet: Image, index: Int)
    {
        if(planet.x >= sceneWidth - planet.width) {
            direction[index] = true
        }
        if(planet.x <= 0 )
        {
            direction[index] = false
        }
        if(direction[index])
        {
            planet.x -= (index + 1)
        } else
        {
            planet.x += (index + 1)
        }
    }
}
