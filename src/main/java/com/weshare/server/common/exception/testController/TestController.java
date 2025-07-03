package com.weshare.server.common.exception.testController;

import com.weshare.server.common.exception.globalException.GlobalException;
import com.weshare.server.common.exception.globalException.GlobalExceptions;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/global-exception")
    public void throwGlobalException() {
        throw new GlobalException(GlobalExceptions.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/generic-exception")
    public void throwGenericException() throws Exception {
        throw new Exception("Generic test exception");
    }

    // HttpMessageNotReadableException은 MockMvc에서 직접 발생시키므로 컨트롤러 메서드는 불필요

    @GetMapping("/missing-param")
    public void missingParam(@RequestParam String requiredParam) {
        // 이 핸들러는 호출되지 않음
    }

    @GetMapping("/type-mismatch/{id}")
    public void typeMismatch(@PathVariable Long id) {
        // 이 핸들러는 호출되지 않음
    }

    @PostMapping(value = "/method-not-allowed", consumes = "application/json")
    public void methodNotAllowed() {
        // 이 핸들러는 POST 요청에만 응답
    }

    @PostMapping(value = "/media-type-not-supported", consumes = "application/json")
    public void mediaTypeNotSupported(@RequestBody String body) {
        // 이 핸들러는 application/json 만 허용
    }
}