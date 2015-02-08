package com.libqa.web.controller

import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

/**
 * Created by sjune on 2015-02-08.
 */
class HelloWorldControllerTest extends Specification {
    MockMvc mockMvc

    def setup() {
        mockMvc = standaloneSetup(new HelloWorldController()).build()
    }

    def '/hello 를 호출하면 world.hbs와 매핑된다.'() {
        when:
        def response = mockMvc.perform(get('/hello'))

        then:
        response.andExpect(status().isOk())
                .andExpect(view().name("world"))
    }

    def '/hello/{message} 를 호출하면 JSON 의 greeting 속성에 {message}을 세팅하여 응답한다.'() {
        when:
        def response = mockMvc.perform(get('/hello/world'))

        then:
        response.andExpect(status().isOk())
                .andExpect(content().contentType('application/json;charset=UTF-8'))
                .andExpect(content().string('{"greeting":"world"}'))
    }
}
