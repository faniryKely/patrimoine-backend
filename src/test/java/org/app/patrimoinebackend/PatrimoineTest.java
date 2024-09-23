package org.app.patrimoinebackend;

import lombok.extern.slf4j.Slf4j;
import org.app.patrimoinebackend.Controller.PatrimoineController;
import org.app.patrimoinebackend.Model.Patrimoine;
import org.app.patrimoinebackend.Service.PatrimoineService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest
@Configuration
public class PatrimoineTest {
        @Mock
        private PatrimoineService patrimoineService;

        @InjectMocks
        private PatrimoineController patrimoineController;

        public void PatrimoineControllerTest() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        public void testGetAllPatrimoineSuccess() throws Exception {
            int id = 1;
            Patrimoine patrimoine = new Patrimoine(1,"John Doe", null);
            when(patrimoineService.getPatrimoine(id)).thenReturn(patrimoine);

            ResponseEntity<Patrimoine> response = patrimoineController.getAllPatrimoine(id);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(patrimoine, response.getBody());
        }

        @Test
        public void testGetAllPatrimoineNotFound() throws Exception {
            int id = 1;
            when(patrimoineService.getPatrimoine(id)).thenReturn(null);

            ResponseEntity<Patrimoine> response = patrimoineController.getAllPatrimoine(id);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }

        @Test
        public void testUpdateOrCreatePatrimoineUpdate() throws Exception {
            int id = 1;
            Patrimoine patrimoine = new Patrimoine(4,"Jane Doe", null);
            when(patrimoineService.updatePatrimoine(id, patrimoine)).thenReturn(patrimoine);
            when(patrimoineService.readPatrimoinesFromFile()).thenReturn(List.of(patrimoine));

            ResponseEntity<Patrimoine> response = patrimoineController.updateOrCreatePatrimoine(id, patrimoine);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(patrimoine, response.getBody());
        }

        @Test
        public void testUpdateOrCreatePatrimoineCreate() throws Exception {
            int id = 2;
            Patrimoine patrimoine = new Patrimoine(5,"Jane Doe", null);
            when(patrimoineService.updatePatrimoine(id, patrimoine)).thenReturn(patrimoine);
            when(patrimoineService.readPatrimoinesFromFile()).thenReturn(List.of());

            ResponseEntity<Patrimoine> response = patrimoineController.updateOrCreatePatrimoine(id, patrimoine);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertEquals(patrimoine, response.getBody());
        }

        @Test
        public void testUpdateOrCreatePatrimoineInternalServerError() throws Exception {
            int id = 1;
            Patrimoine patrimoine = new Patrimoine(4,"Jane Doe", null);
            when(patrimoineService.updatePatrimoine(id, patrimoine)).thenThrow(IOException.class);

            ResponseEntity<Patrimoine> response = patrimoineController.updateOrCreatePatrimoine(id, patrimoine);

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
            assertNull(response.getBody());
        }


        @Test
        public void testHomePage() throws Exception {
            String response = patrimoineController.HomePage();
            assertEquals("Bienvenue sur mon patrimoine !", response);
        }
}
