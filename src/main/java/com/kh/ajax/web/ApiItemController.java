package com.kh.ajax.web;

import com.kh.ajax.domain.Item;
import com.kh.ajax.domain.ItemDao;
import com.kh.ajax.domain.ItemDaoImpl;
import com.kh.ajax.web.api.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/api/items")
public class ApiItemController {

  private final ItemDao itemDao = ItemDaoImpl.getItemDaoImpl();

  //상품등록
  @ResponseBody
  @PostMapping
  public ApiResult<Item> save(@RequestBody Item item) {

    Item saveItem = itemDao.save(item);
    ApiResult<Item> result = new ApiResult<>("00", "success", saveItem);
    return result;
  }

  //상품목록
  @ResponseBody
  @GetMapping
  public ApiResult<List<Item>> findAll() {

    List<Item> list = itemDao.findAll();
    ApiResult<List<Item>> result = null;
    if (list.size() > 0) {
      result = new ApiResult<>("00", "success", list);
    } else {
      result = new ApiResult<>("02", "success", null);
    }
    return result;
  }

  //상품조회
  @ResponseBody
  @GetMapping("/{id}")
  public ApiResult<Item> findById(@PathVariable Long id) {
    Item findedItem = itemDao.findById(id);
    ApiResult<Item> result = null;
    if (findedItem != null) {
      result = new ApiResult<>("00", "success", findedItem);
    } else {
      result = new ApiResult<>("99", "fail", null);
    }
    return result;
  }

  //상품삭제
  @ResponseBody
  @DeleteMapping("/{id}")
  public ApiResult<String> delete(@PathVariable Long id) {
    Item deletedItem = itemDao.delete(id);

    ApiResult<String> result = null;
    if (deletedItem != null) {
      result = new ApiResult<>("00", "success", deletedItem.getName() + "가 삭제되었습니다.");
    } else {
      result = new ApiResult<>("99", "fail", "삭제할 상품이 없습니다.");
    }
    return result;
  }

  //상품수정
  @ResponseBody
  @PatchMapping("/{id}")
  public ApiResult<Object> update(@PathVariable Long id,
                                  @RequestBody Item item
  ) {
    Item updateBeforeItem = itemDao.update(id, item);

    ApiResult<Object> result = null;
    if (updateBeforeItem != null) {
      result = new ApiResult<>("00", "success", itemDao.findById(item.getId()));
    } else {
      result = new ApiResult<>("99", "fail", "상품 아이디가 없습니다.");
    }
    return result;
  }


  @PostConstruct  //빈생성후 후처리
  public void init() {
    Item item = new Item();
    item.setName("키보드1");
    item.setBrand("LG");
    item.setDesc("LG키보드-1");
    item.setPrice(100000L);
    itemDao.save(item); //id = 1

    item = new Item();
    item.setName("키보드2");
    item.setBrand("LG");
    item.setDesc("LG키보드-2");
    item.setPrice(150000L);
    itemDao.save(item); //id = 2
  }

}
