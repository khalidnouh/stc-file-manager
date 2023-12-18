package com.stc.STCFilesBackend.service.impl;

import com.stc.STCFilesBackend.domain.Item;
import com.stc.STCFilesBackend.domain.PermissionGroup;
import com.stc.STCFilesBackend.repository.ItemRepository;
import com.stc.STCFilesBackend.service.ItemService;
import com.stc.STCFilesBackend.service.PermissionsService;
import com.stc.STCFilesBackend.service.dto.ItemDTO;
import com.stc.STCFilesBackend.service.dto.PermissionsDTO;
import com.stc.STCFilesBackend.service.enums.ItemType;
import com.stc.STCFilesBackend.service.mapper.ItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    private final Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);

    private final ItemRepository itemRepository;

    private final ItemMapper itemMapper;

    @Autowired
    PermissionsService permissionsService;

    public ItemServiceImpl(ItemRepository itemRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    @Override
    public ItemDTO save(ItemDTO itemDTO) {
        log.debug("Request to save Item : {}", itemDTO);
        Item item = itemMapper.toEntity(itemDTO);
        //
        item = itemRepository.save(item);
        return itemMapper.toDto(item);
    }

    @Override
    public Optional<ItemDTO> partialUpdate(ItemDTO itemDTO) {
        log.debug("Request to partially update Item : {}", itemDTO);

        return itemRepository
                .findById(itemDTO.getId())
                .map(existingItem -> {
                    itemMapper.partialUpdate(existingItem, itemDTO);
                    return existingItem;
                })
                .map(itemRepository::save)
                .map(itemMapper::toDto);
    }

    @Override
    public List<ItemDTO> findAll() {
        List<Item> items = itemRepository.findAll();
        return items.stream().map(itemMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all items");
        return itemRepository.findAll(pageable).map(itemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ItemDTO> findOne(Long id) {
        log.debug("Request to get Item : {}", id);
        Optional<ItemDTO> itemDTO =  itemRepository.findById(id).map(itemMapper::toDto);
        //
        String itemType = itemDTO.get().getType();
        if (!StringUtils.isEmpty(itemType)) {
            String item_type = ItemType.getItemType(itemType).name();
            if (itemType.length() == 2 && itemType != null) {
                itemDTO.get().setType(itemType);
            }
        }
        return itemDTO;
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete item : {}", id);
        itemRepository.deleteById(id);
    }

    @Override
    public ItemDTO saveSpaceItem(ItemDTO itemDTO) throws Exception {
        itemDTO.setType(ItemType.SPACE.getValue());
        if (itemDTO.getPermission_group_id() > 0) {
            List<PermissionsDTO> permissionsDTOS = permissionsService.findAllByPermissionGroup(itemDTO.getPermission_group_id());
            if (permissionsDTOS.size() > 0) {
                permissionsDTOS = permissionsDTOS
                        .stream()
                        .filter(permission ->
                                "VIEW".equals(permission.getPermissionLevel()) ||
                                "EDIT".equals(permission.getPermissionLevel()))
                        .collect(Collectors.toList());
                if (permissionsDTOS.size() < 0) {
                    throw new Exception("ThisGroupDon'tHavePermissionToViewOrEdit");
                }
            } else {
                throw new Exception("ThisItemGroupDon'tHavePermissions");
            }
        }
        return save(itemDTO);
    }

    @Override
    public ItemDTO saveItemWithParent(ItemDTO itemDTO, ItemDTO parentDTO) throws Exception {
        if (parentDTO.getId() > 0) {
            itemDTO.setParent_id(parentDTO.getId());
        } else {
            throw new Exception("ParentNotFound");
        }
        if (itemDTO.getPermission_group_id() > 0) {
            List<PermissionsDTO> permissionsDTOS = permissionsService.findAllByPermissionGroup(itemDTO.getPermission_group_id());
            if (permissionsDTOS.size() > 0) {
                permissionsDTOS = permissionsDTOS
                        .stream()
                        .filter(permission ->
                                        "EDIT".equals(permission.getPermissionLevel()))
                        .collect(Collectors.toList());
                if (permissionsDTOS.size() < 0) {
                    throw new Exception("ThisGroupDon'tHavePermissionToEdit");
                }
            } else {
                throw new Exception("ThisItemGroupDon'tHavePermissions");
            }
        }
        return save(itemDTO);
    }

}
