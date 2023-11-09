package com.masai.Controller;

import com.masai.Controller.UserController;
import com.masai.Controller.TaskController;
import com.masai.Entity.Users;
import com.masai.Entity.Task;
import com.masai.Exception.UserException;
import com.masai.Exception.TaskException;
import com.masai.Service.UserService;
import com.masai.Service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;

@WebMvcTest({UserController.class, TaskController.class})
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @InjectMocks
    private TaskController taskController;

    @Mock
    private UserService userService;

    @Mock
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController, taskController).build();
    }

    @Test
    public void testCreateUser() throws Exception {
        Users user = new Users();
        user.setName("John Doe");
        user.setEmail("j@gmail.com");
        user.setPassword("john@1234");

        Mockito.when(userService.registerUser(Mockito.any(Users.class))).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                .contentType("application/json")
                .content("{\"name\":\"John Doe\",\"email\":\"johndoe@example.com\",\"password\":\"password123\"}")
            )
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"));

        verify(userService).registerUser(Mockito.any(Users.class));
    }

    @Test
    public void testCreateTask() throws Exception {
        Task task = new Task();
        task.setId(1);
        task.setTitle("Sample Task");

        Mockito.when(taskService.createTask(Mockito.any(Task.class))).thenReturn(task);

        mockMvc.perform(MockMvcRequestBuilders.post("/tasks/create")
                .contentType("application/json")
                .content("{\"title\":\"Sample Task\",\"description\":\"Sample Description\"}")
            )
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Sample Task"));

        verify(taskService).createTask(Mockito.any(Task.class));
    }
}

