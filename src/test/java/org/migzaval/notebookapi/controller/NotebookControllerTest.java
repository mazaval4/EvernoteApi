package org.migzaval.notebookapi.controller;

import org.junit.runner.RunWith;
import org.migzaval.notebookapi.service.NotebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(value = NotebookController.class, secure = false)
public class NotebookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private NotebookService notebookService;

}
