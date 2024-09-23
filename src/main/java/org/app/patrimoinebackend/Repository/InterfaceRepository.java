package org.app.patrimoinebackend.Repository;

import org.app.patrimoinebackend.Model.Patrimoine;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public interface InterfaceRepository {
    Patrimoine updatePatrimoine(int Id, Patrimoine patrimoine) throws IOException;
    Patrimoine getPatrimoine(int id) throws IOException;


}
