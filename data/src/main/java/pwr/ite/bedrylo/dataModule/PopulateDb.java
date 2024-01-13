package pwr.ite.bedrylo.dataModule;

import pwr.ite.bedrylo.dataModule.dto.CommodityDto;
import pwr.ite.bedrylo.dataModule.dto.ReceiptDto;
import pwr.ite.bedrylo.dataModule.dto.UserDto;
import pwr.ite.bedrylo.dataModule.model.data.enums.Role;
import pwr.ite.bedrylo.dataModule.repository.CommodityRepository;
import pwr.ite.bedrylo.dataModule.repository.ReceiptRepository;
import pwr.ite.bedrylo.dataModule.repository.UserRepository;
import pwr.ite.bedrylo.dataModule.repository.implementations.commodity.CommodityRepositoryJPAImplementation;
import pwr.ite.bedrylo.dataModule.repository.implementations.receipt.ReceiptRepositoryJPAImplementation;
import pwr.ite.bedrylo.dataModule.repository.implementations.user.UserRepositoryJPAImplementation;
import pwr.ite.bedrylo.dataModule.service.CommodityService;
import pwr.ite.bedrylo.dataModule.service.ReceiptService;
import pwr.ite.bedrylo.dataModule.service.UserService;

import java.util.*;

public class PopulateDb {
    
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepositoryJPAImplementation();
        ReceiptRepository receiptRepository = new ReceiptRepositoryJPAImplementation();
        CommodityRepository commodityRepository = new CommodityRepositoryJPAImplementation();
        UserService userService = UserService.getInstance();
        ReceiptService receiptService = ReceiptService.getInstance();
        CommodityService commodityService = CommodityService.getInstance();
        
//        userRepository.save(userService.createUserFromDto(new UserDto(2137, "test1klient", Role.CLIENT, UUID.fromString("4927fca0-b14c-4a2e-bee7-274dbb95325f"))));
//        userRepository.save(userService.createUserFromDto(new UserDto(69, "test2dostawca", Role.DELIVERER)));
//        userRepository.save(userService.createUserFromDto(new UserDto(420, "test3sprzedawca", Role.SELLER)));
//        
//
//        List<CommodityDto> commodityDtos = new ArrayList<>();
//        commodityDtos.add(new CommodityDto("towar1", null, UUID.fromString("62b014a4-95f8-4f98-a099-19e792d8ae72"), 11));
//        commodityDtos.add(new CommodityDto("towar2", null, UUID.randomUUID(), 12));
//        commodityRepository.save(commodityService.createCommodityFromDTO(commodityDtos.get(0)));
//        commodityRepository.save(commodityService.createCommodityFromDTO(commodityDtos.get(1)));
        
        for (int i = 0; i < 100; i++) {
            commodityRepository.save(commodityService.createCommodityFromDTO(new CommodityDto("towar" + i, null, UUID.randomUUID(), 10 + Math.random()*100+1)));
        }
//        receiptRepository.save(receiptService.createReceiptFromDto(new ReceiptDto(UUID.randomUUID(), commodityDtos)));
//        
        
        
    }
    
    
}
