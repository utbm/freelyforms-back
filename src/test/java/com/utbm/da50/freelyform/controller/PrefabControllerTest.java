package com.utbm.da50.freelyform.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

public class PrefabControllerTest {
/*
    @Mock
    private PrefabService service;

    @InjectMocks
    private PrefabController controller;

    private Group groupTest;

    @BeforeEach
    public void setup() {
        groupTest = new Group();
        groupTest.setId("1");
        groupTest.setName("Test Group");
        Field field = new Field();
        field.setId("1");
        field.setLabel("Test Field");
        groupTest.setFields(Collections.singletonList(field));
        MockitoAnnotations.openMocks(this);
    }

    // Helper method to create Prefab with a builder pattern
    private Prefab createPrefab(String id) {
        return Prefab.builder()
                .id(id)
                .name("Prefab Name")
                .description("Prefab Description")
                .build();
    }

    @Test
    public void testGetAllPrefabs() {
        Prefab prefab = createPrefab("1");
        Group group = Mockito.mock(Group.class);
        prefab.setGroups(Collections.singletonList(group));
        List<Prefab> prefabList = Collections.singletonList(prefab);

        when(service.getFilteredPrefabs(any(PrefabFilter.class))).thenReturn(prefabList);

        ResponseEntity<List<PrefabOutput>> response = controller.getAllPrefabs(
                null, false, null, 0, 10);

        assertThat(response.getBody(), hasSize(1));
        assertThat(response.getBody().get(0).getId(), is(equalTo("1")));
    }

    @Test
    public void testGetPrefabById_Success() {
        Prefab prefab = createPrefab("1");
        Group group = Mockito.mock(Group.class);
        prefab.setGroups(Collections.singletonList(group));

        when(service.getPrefabById("1", false)).thenReturn(prefab);

        ResponseEntity<PrefabOutput> response = controller.getPrefabById("1", false);

        assertThat(response.getBody().getId(), is(equalTo("1")));
    }

    @Test
    public void testGetPrefabById_NotFound() {
        when(service.getPrefabById("1", false)).thenThrow(new NoSuchElementException("Prefab not found"));

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> controller.getPrefabById("1", false)
        );

        assertThat(exception.getMessage(), is(equalTo("404 NOT_FOUND \"Prefab not found\"")));

    }

    @Test
    public void testCreatePrefab_Success() throws ValidationFieldException {
        Prefab prefab = createPrefab("1");

        prefab.setGroups(Collections.singletonList(groupTest));

        when(service.createPrefab(any(Prefab.class))).thenReturn(prefab);

        PrefabInput newPrefab = new PrefabInput();
        newPrefab.setName("Test Prefab");
        List<GroupInput> groups = Collections.singletonList(new GroupInput());
        newPrefab.setGroups(groups);


        ResponseEntity<PrefabOutput> response = controller.createPrefab(newPrefab);

        assertThat(response.getBody().getId(), is(equalTo("1")));
    }

    @Test
    public void testCreatePrefab_ValidationException() throws ValidationFieldException {
        when(service.createPrefab(any(Prefab.class))).thenThrow(new ValidationFieldException("Validation failed"));

        PrefabInput newPrefab = new PrefabInput();
        newPrefab.setName("Invalid Prefab");

        ValidationFieldException exception = assertThrows(
                ValidationFieldException.class,
                () -> controller.createPrefab(newPrefab)
        );

        assertThat(exception.getMessage(), is(equalTo("Validation failed")));
    }

    @Test
    public void testUpdatePrefab_Success() throws ValidationFieldException {
        Prefab prefab = createPrefab("1");

        when(service.updatePrefab(any(String.class), any(Prefab.class))).thenReturn(prefab);

        PrefabInput updatedPrefab = new PrefabInput();
        updatedPrefab.setName("Updated Prefab");

        ResponseEntity<PrefabOutput> response = controller.updatePrefab("1", updatedPrefab);

        assertThat(response.getBody().getId(), is(equalTo("1")));
    }

    @Test
    public void testDeletePrefab_Success() {
        Prefab prefab = createPrefab("1");

        when(service.deletePrefab("1")).thenReturn(prefab);

        ResponseEntity<PrefabOutput> response = controller.deletePrefab("1");

        assertThat(response.getBody().getId(), is(equalTo("1")));
    }

    @Test
    public void testDeletePrefab_NotFound() {
        when(service.deletePrefab("1")).thenThrow(new NoSuchElementException("Prefab not found"));

        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> controller.deletePrefab("1")
        );

        assertThat(exception.getMessage(), is(equalTo("Prefab not found")));
    }*/
}
