package com.kyriba.submittal.api;

import com.kyriba.submittal.service.KeyGenerator;
import com.kyriba.submittal.service.KeyType;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author M-ABL
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("inttest")
class SpeakerControllerIntTest
{
  @Autowired
  protected WebApplicationContext context;

  private MockMvc mockMvc;

  @MockBean
  private KeyGenerator keyGenerator;


  @BeforeEach
  void setUp()
  {
    mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
  }


  @Test
  void register() throws Exception
  {
    //language=JSON
    String registerContent = "{\n" +
        "  \"email\": \"j@s.com\",\n" +
        "  \"password\": \"12345678\",\n" +
        "  \"personalInfo\": {\n" +
        "    \"firstName\": \"John\",\n" +
        "    \"lastName\": \"Snow\",\n" +
        "    \"title\": \"Bastard\",\n" +
        "    \"phone\": \"+375-29-1111111\"\n" +
        "  },\n" +
        "  \"jobInfo\": {\n" +
        "    \"position\": \"King\",\n" +
        "    \"company\": \"Stark&Co\",\n" +
        "    \"city\": \"Winterfell\",\n" +
        "    \"country\": \"North\"\n" +
        "  },\n" +
        "  \"conferenceId\": 1001,\n" +
        "  \"topics\": [\n" +
        "    {\n" +
        "      \"title\": \"Nice topic\",\n" +
        "      \"description\": \"You should listen to it\"\n" +
        "    }\n" +
        "  ]\n" +
        "}";

    String confirmationKey = RandomStringUtils.random(10);
    given(keyGenerator.generate(KeyType.EMAIL)).willReturn(confirmationKey);
    // register a new speaker with some topics
    mockMvc
        .perform(post("/api/v1/speakers/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(registerContent))
        .andExpect(status().isOk())
        .andExpect(content().json("{\"speakerId\":1,\"topics\":[2]}"));

    // try to register a new speaker with the same email
    mockMvc
        .perform(post("/api/v1/speakers/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(registerContent))
        .andExpect(status().isConflict());

    // finish registration
    mockMvc
        .perform(post("/api/v1/confirm/registration")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"key\":\"" + confirmationKey + "\"}"))
        .andExpect(status().isOk());

    // recall the last request > 404
    mockMvc
        .perform(post("/api/v1/confirm/registration")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"key\":\"" + confirmationKey + "\"}"))
        .andExpect(status().isNotFound())
        .andDo(print());

    // get list of topics
    mockMvc
        .perform(get("/api/v1/speakers/1/topics")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(
            "[{\"topicId\":2,\"status\":\"REQUESTED\",\"title\":\"Nice topic\",\"description\":\"You should listen to it\",\"conferenceId\":1001}]"));

    // create a new topic
    //language=JSON
    String topicContent = "{\n" +
        "  \"conferenceId\": 1002,\n" +
        "  \"topics\": [\n" +
        "    {\n" +
        "      \"title\": \"Next topic\",\n" +
        "      \"description\": \"Optional\"\n" +
        "    }\n" +
        "  ]\n" +
        "}";
    mockMvc
        .perform(post("/api/v1/speakers/1/topics")
            .contentType(MediaType.APPLICATION_JSON)
            .content(topicContent))
        .andExpect(status().isOk())
        .andExpect(content().json("{\"topics\": [3]}"));

    // approve a topic
    mockMvc
        .perform(put("/api/v1/topics/3/status")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"status\": \"APPROVED\"}"))
        .andExpect(status().isOk());

    // get list of topics
    mockMvc
        .perform(get("/api/v1/speakers/1/topics")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(
            "[{\"topicId\":2,\"status\":\"REQUESTED\",\"title\":\"Nice topic\",\"description\":\"You should listen to it\",\"conferenceId\":1001}," +
                "{\"topicId\":3,\"status\":\"APPROVED\",\"title\":\"Next topic\",\"description\":\"Optional\",\"conferenceId\":1002}]"));

    // reject a topic
    mockMvc
        .perform(put("/api/v1/topics/2/status")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"status\": \"REJECTED\"}"))
        .andExpect(status().isOk());

    // get list of topics for a conference
    mockMvc
        .perform(get("/api/v1/speakers/1/conferences/1001/topics")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(
            "[{\"topicId\":2,\"status\":\"REJECTED\",\"title\":\"Nice topic\",\"description\":\"You should listen to it\",\"conferenceId\":1001}]"));

    // get a personal info
    mockMvc
        .perform(get("/api/v1/speakers/1/personal")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(
            "{\"title\":\"Bastard\",\"firstName\":\"John\",\"lastName\":\"Snow\",\"phone\":\"+375-29-1111111\"}"));

    // update the personal info
    mockMvc
        .perform(put("/api/v1/speakers/1/personal")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\"title\":null,\"firstName\":\"John\",\"lastName\":\"Snow\",\"phone\":\"+375-29-2222222\"}"))
        .andExpect(status().isOk());

    // get an updated personal info
    mockMvc
        .perform(get("/api/v1/speakers/1/personal")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(
            "{\"title\":null,\"firstName\":\"John\",\"lastName\":\"Snow\",\"phone\":\"+375-29-2222222\"}"));

    // update an incorrect personal info
    mockMvc
        .perform(put("/api/v1/speakers/1/personal")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\"title\":null,\"firstName\":null,\"lastName\":\"Snow\",\"phone\":\"+375-29-2222222\"}"))
        .andExpect(status().isBadRequest());

//    System.out.println(result);
  }
}
