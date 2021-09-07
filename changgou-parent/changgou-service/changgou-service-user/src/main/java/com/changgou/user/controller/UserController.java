package com.changgou.user.controller;
import com.alibaba.fastjson.JSON;
import com.changgou.user.pojo.User;
import com.changgou.user.service.UserService;
import com.github.pagehelper.PageInfo;
import entity.BCrypt;
import entity.JwtUtil;
import entity.Result;
import entity.StatusCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/****
 * @Author:admin
 * @Description:
 * @Date 2019/6/14 0:18
 *****/

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    /***
     * User��ҳ��������ʵ��
     * @param user
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false)  User user, @PathVariable  int page, @PathVariable  int size){
        //����UserServiceʵ�ַ�ҳ������ѯUser
        PageInfo<User> pageInfo = userService.findPage(user, page, size);
        return new Result(true,StatusCode.OK,"��ѯ�ɹ�",pageInfo);
    }

    /***
     * User��ҳ����ʵ��
     * @param page:��ǰҳ
     * @param size:ÿҳ��ʾ������
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size){
        //����UserServiceʵ�ַ�ҳ��ѯUser
        PageInfo<User> pageInfo = userService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"��ѯ�ɹ�",pageInfo);
    }

    /***
     * ����������Ʒ������
     * @param user
     * @return
     */
    @PostMapping(value = "/search" )
    public Result<List<User>> findList(@RequestBody(required = false)  User user){
        //����UserServiceʵ��������ѯUser
        List<User> list = userService.findList(user);
        return new Result<List<User>>(true,StatusCode.OK,"��ѯ�ɹ�",list);
    }

    /***
     * ����IDɾ��Ʒ������
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable String id){
        //����UserServiceʵ�ָ�������ɾ��
        userService.delete(id);
        return new Result(true,StatusCode.OK,"ɾ���ɹ�");
    }

    /***
     * �޸�User����
     * @param user
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody  User user,@PathVariable String id){
        //��������ֵ
        user.setUsername(id);
        //����UserServiceʵ���޸�User
        userService.update(user);
        return new Result(true,StatusCode.OK,"�޸ĳɹ�");
    }

    /***
     * ����User����
     * @param user
     * @return
     */
    @PostMapping
    public Result add(@RequestBody   User user){
        //����UserServiceʵ�����User
        userService.add(user);
        return new Result(true,StatusCode.OK,"��ӳɹ�");
    }

    /***
     * ����ID��ѯUser����
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<User> findById(@PathVariable String id){
        //����UserServiceʵ�ָ���������ѯUser
        User user = userService.findById(id);
        return new Result<User>(true,StatusCode.OK,"��ѯ�ɹ�",user);
    }

    /***
     * ��ѯUserȫ������
     * @return
     */
    @GetMapping
    public Result<List<User>> findAll(){
        //����UserServiceʵ�ֲ�ѯ����User
        List<User> list = userService.findAll();
        return new Result<List<User>>(true, StatusCode.OK,"��ѯ�ɹ�",list) ;
    }

    /***
     * �û���¼
     */
    @RequestMapping(value = "/login")
    public Result login(String username,String password, HttpServletResponse response){
        // �����û����������ѯ�û���Ϣ
        User user = userService.findById(username);
        if (user==null){
            return  new Result(false,StatusCode.LOGINERROR,"�˺Ż����������");
        }
        // У���û����������Ƿ������ݿ��һ��
        // �Ƚ�ǰ�˴��ݵ�����������ܣ��������ݿ���������ƥ��
        // BCrypt.checkpw(password,user.getPassword())��У�����룬����1���������룬����2�����ݿ���������
        if (!BCrypt.checkpw(password,user.getPassword())){
            return  new Result(false,StatusCode.LOGINERROR,"�˺Ż����������");
        }
        // ���һ�£���¼�ɹ�
        // ��¼�ɹ�����������token
        //����������Ϣ
        Map<String,Object> info = new HashMap<String,Object>();
        info.put("role","USER");
        info.put("success","SUCCESS");
        info.put("username",username);
        // ��������
        String jwt = JwtUtil.createJWT(UUID.randomUUID().toString(), JSON.toJSONString(info),null);

        //��������Ϣ�����û���cookie  ��key ����Authorization ֵ�������Ʊ���
        Cookie cookie = new Cookie("Authorization",jwt);
        response.addCookie(cookie);
        return  new Result(true,StatusCode.OK,"��¼�ɹ���",jwt);

    }
}
