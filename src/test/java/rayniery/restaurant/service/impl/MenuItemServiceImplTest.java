package rayniery.restaurant.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import rayniery.restaurant.api.model.MenuItemResponse;
import rayniery.restaurant.persistance.entity.MenuItemEntity;
import rayniery.restaurant.persistance.repository.MenuItemRepository;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class MenuItemServiceImplTest {
    @Mock
    private MenuItemRepository repository;

    @InjectMocks
    MenuItemServiceImpl service;

    @Test
    public void shouldReturnAllMenuItems() {
        MenuItemEntity entity = MenuItemEntity.builder()
                .additionalDetails("blah")
                .image("image url")
                .name("Dumpling")
                .price(BigDecimal.TEN)
                .build();
        Page<MenuItemEntity> page = new PageImpl<>(Arrays.asList(entity));

        given(repository.findAll(Pageable.unpaged()))
                .willReturn(page);

        MenuItemResponse response = service.getMenuItems(Pageable.unpaged(), "");

        assertThat(response.getMenuItems()).hasSize(1);
        assertThat(response.getMenuItems().get(0)).isEqualToComparingFieldByField(entity);
    }
}