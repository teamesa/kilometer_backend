package com.kilometer.domain.user

import com.kilometer.domain.image.ImageService
import com.kilometer.domain.user.dto.UserProfileUpdate
import spock.lang.Specification
import spock.lang.Subject

class UserServiceTest extends Specification {
    @Subject
    UserService sut

    UserFormValidator userFormValidator = Mock(UserFormValidator.class)
    UserRepository userRepository = Mock(UserRepository.class)
    NameGenerator nameGenerator = Mock(NameGenerator.class)
    ImageService imageService = Mock(ImageService.class)

    def setup() {
        sut = new UserService(nameGenerator, userFormValidator, userRepository, imageService)
    }

    def 'updateUserProfile works well'() {
        given:
        def userId = 1L
        def profileUpdate = UserProfileUpdate.builder().userId(userId).build()
        def user = User.builder().id(userId).imageUrl("ex").build()
        def profileUrl = "profile-url"
        def updatedUser = User.builder().id(userId).imageUrl(profileUrl).build()
        1 * imageService.upload(*_) >> profileUrl
        1 * userRepository.findById(userId) >> Optional.of(user)
        1 * userRepository.save({ userId == userId }) >> updatedUser
        when:
        def result = sut.updateUserProfile(profileUpdate)

        then:
        result
        result.get().imageUrl == profileUrl
        result.get().id == userId
    }
}
