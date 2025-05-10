package com.siemens.internship;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class InternshipApplicationTests {
	// The ItemService is the main service class that handles business logic
	@Autowired
	private ItemService itemService;

	// Test to check if the application context loads successfully
	@Test
	void contextLoads() {
		assertThat(itemService).isNotNull();
	}

	// Test for getting an item by ID
	@Test
	void testGetItemById() {
		Item newItem = new Item(null, "Item1", "Description1", "NEW", "test@example.com");
		Item createdItem = itemService.save(newItem);
		Long itemId = createdItem.getId();

		Item fetchedItem = itemService.findById(itemId).orElse(null);
		assertThat(fetchedItem).isNotNull();
		assertThat(fetchedItem.getName()).isEqualTo("Item1");
	}

	// Test for getting all items
	@Test
	void testGetAllItems() {
		List<Item> items = itemService.findAll();
		assertThat(items).isNotNull();
	}

	// Test for creating a new item
	@Test
	void testCreateItem() {
		Item newItem = new Item(null, "Item1", "Description1", "NEW", "test@example.com");
		Item createdItem = itemService.save(newItem);
		assertThat(createdItem).isNotNull();
		assertThat(createdItem.getName()).isEqualTo("Item1");
	}

	// Test for updating an existing item
	@Test
	void testUpdateItem() {
		Item newItem = new Item(null, "Item1", "Description1", "NEW", "test@example.com");
		Item createdItem = itemService.save(newItem);
		Long itemId = createdItem.getId();

		createdItem.setName("UpdatedItem");
		Item updatedItem = itemService.save(createdItem);

		assertThat(updatedItem).isNotNull();
		assertThat(updatedItem.getName()).isEqualTo("UpdatedItem");
	}

	// Test for deleting an item
	@Test
	void testDeleteItem() {
		Item newItem = new Item(null, "Item1", "Description1", "NEW", "test@example.com");
		Item createdItem = itemService.save(newItem);
		Long itemId = createdItem.getId();

		itemService.deleteById(itemId);
		assertThat(itemService.findById(itemId)).isEmpty();
	}

	// Test for processing items asynchronously
	@Test
	void testProcessItems() throws Exception {
		Item item1 = new Item(null, "Item1", "Description1", "NEW", "test1@example.com");
		Item item2 = new Item(null, "Item2", "Description2", "NEW", "test2@example.com");
		itemService.save(item1);
		itemService.save(item2);

		List<Item> processedItems = itemService.processItemsAsync().get();
		assertThat(processedItems).isNotNull();
		assertThat(processedItems.size()).isGreaterThanOrEqualTo(2);
		assertThat(processedItems.get(0).getStatus()).isEqualTo("PROCESSED");
	}

}
