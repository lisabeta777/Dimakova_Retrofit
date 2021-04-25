import clients.petstore.PetStore;
import clients.petstore.PetStoreService;
import models.pet.Category;
import models.pet.Pet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class AutoTests {

    PetStore petStore = new PetStoreService().getPetStore();

    public Pet create_myPet() {
        Pet myPet = new Pet();
        myPet.setId(100);
        myPet.setName("Murzic");
        Category category = new Category();
        category.setName("Cat");
        myPet.setCategory(category);
        myPet.setStatus("available");
        return myPet;
    }


    @Test
    public void postTest() throws IOException {
        Pet myPet = create_myPet();
        Pet retrofitPet = petStore.createPet(myPet).execute().body();
        Assertions.assertEquals(myPet, retrofitPet);
    }

    @Test
    public void getTest200() throws IOException {
        Pet myPet = create_myPet();

        Pet retrofitPet = petStore.createPet(myPet).execute().body();
        Pet resultPet = petStore.getPetById(100).execute().body();
        Assertions.assertEquals(myPet, resultPet);
    }

    @Test
    public void getTest404() throws IOException {
        Pet myPet = create_myPet();
        Pet retrofitPet = petStore.createPet(myPet).execute().body();
        petStore.deletePet(100).execute();
        int result = petStore.getPetById(100).execute().code();
        Assertions.assertEquals(404, result);
    }

    @Test
    public void put200() throws IOException {
        Pet myPet = create_myPet();
        Pet myPet_update = new Pet();
            myPet_update.setId(100);
            myPet_update.setName("Пушок");
            Category category_update = new Category();
            category_update.setName("Собака");
            myPet_update.setCategory(category_update);
            myPet.setStatus("sold");

        Pet retrofitPet = petStore.createPet(myPet).execute().body();
        Pet putPet = petStore.putPet(myPet_update).execute().body();
        Pet resultPet = petStore.getPetById(100).execute().body();
        Assertions.assertEquals(myPet_update, resultPet);
    }

    @Test
    public void put404() throws IOException {
        Pet myPet = create_myPet();
        Pet myPet_update = new Pet();
        myPet_update.setId(101);
        myPet_update.setName("Соня");
        Category category_update = new Category();
        category_update.setName("Кошка");
        myPet_update.setCategory(category_update);
        myPet.setStatus("sold");

        Pet retrofitPet = petStore.createPet(myPet).execute().body();
        int putPet = petStore.putPet(myPet_update).execute().code();
        Assertions.assertEquals(404, putPet);
    }

    @Test
    public void delete200() throws IOException {
        Pet myPet = create_myPet();

        Pet retrofitPet = petStore.createPet(myPet).execute().body();
        int delete = petStore.deletePet(100).execute().code();
        Assertions.assertEquals(200, delete);
    }

    @Test
    public void delete404() throws IOException {
        Pet myPet = create_myPet();

        Pet retrofitPet = petStore.createPet(myPet).execute().body();
        petStore.deletePet(100).execute();
        int delete = petStore.deletePet(100).execute().code();
        Assertions.assertEquals(404, delete);
    }
}
