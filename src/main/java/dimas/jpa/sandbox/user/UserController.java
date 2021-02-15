package dimas.jpa.sandbox.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    /*
        00) ! IDEA JBA CONFIG !

        01) сервис дублирует репозиторий
        02) репозиторий в контроллере
        03) и репозиторий и сервис в контроллере
        04) что принимать/возвращать по ресту
        05) рест по стандарту или по договорённости
        06) а какой стандарт
        07) названия методов в контроллере
        08) где строить запросы
        09) оверхед кверидсл
        10) что контроллер передаёт сервису (User/UserDto/UserRequest/fields)
        11) что сервис отдаёт контроллеру (List/Page/Optional/null/Exception)
        12) валидация в контроллере или сервисе
        13) в сервисе использовать сервисы или репозитории
        14) инициализация поля и билдер
        15) коллекции в полях энтити
        16) каскады
        17) джоины jpql с дубликатами
        18) @Repository (QuerydslRepositorySupport)
     */

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> get() {
        return ResponseEntity.ok(userService.findAll());
    }
}
