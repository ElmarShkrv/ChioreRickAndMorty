import com.example.chiorerickandmorty.data.model.Characters
import com.example.chiorerickandmorty.data.model.Result
import com.example.chiorerickandmorty.domain.mappers.EpisodeMapper
import com.example.chiorerickandmorty.domain.models.Character


object CharachterMapper {

    fun buildFrom(
        response: Characters,
        episodes: List<Result>
    ): Character {
        return Character(
            episodeList = episodes.map {
                EpisodeMapper.buildFrom(it, emptyList())
            },
            gender = response.gender,
            id = response.id,
            image = response.image,
            loacation = Character.Location(
                name = response.location.name,
                url = response.location.url
            ),
            name = response.name,
            origin = Character.Origin(
                name = response.origin.name,
                url = response.origin.url
            ),
            species = response.species,
            status = response.status
        )
    }

}