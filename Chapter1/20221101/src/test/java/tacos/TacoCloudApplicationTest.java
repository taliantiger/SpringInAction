package tacos;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(TacoCloudApplication.class)
public class TacoCloudApplicationTest {
    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testTaco() throws Exception{
        mockMvc.perform(get("/"))

                .andExpect(status().isOk())

                .andExpect(view().name("home"))

                .andExpect(content().string(
                        containString("Welcome to...")
                ));
    }
}
