package com.changgou.user.service.impl;
import com.changgou.user.dao.UserMapper;
import com.changgou.user.pojo.User;
import com.changgou.user.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import java.util.List;
/****
 * @Author:admin
 * @Description:Userҵ���ӿ�ʵ����
 * @Date 2019/6/14 0:16
 *****/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    /**
     * User����+��ҳ��ѯ
     * @param user ��ѯ����
     * @param page ҳ��
     * @param size ҳ��С
     * @return ��ҳ���
     */
    @Override
    public PageInfo<User> findPage(User user, int page, int size){
        //��ҳ
        PageHelper.startPage(page,size);
        //������������
        Example example = createExample(user);
        //ִ������
        return new PageInfo<User>(userMapper.selectByExample(example));
    }

    /**
     * User��ҳ��ѯ
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<User> findPage(int page, int size){
        //��̬��ҳ
        PageHelper.startPage(page,size);
        //��ҳ��ѯ
        return new PageInfo<User>(userMapper.selectAll());
    }

    /**
     * User������ѯ
     * @param user
     * @return
     */
    @Override
    public List<User> findList(User user){
        //������ѯ����
        Example example = createExample(user);
        //���ݹ�����������ѯ����
        return userMapper.selectByExample(example);
    }


    /**
     * User������ѯ����
     * @param user
     * @return
     */
    public Example createExample(User user){
        Example example=new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        if(user!=null){
            // �û���
            if(!StringUtils.isEmpty(user.getUsername())){
                    criteria.andLike("username","%"+user.getUsername()+"%");
            }
            // ���룬���ܴ洢
            if(!StringUtils.isEmpty(user.getPassword())){
                    criteria.andEqualTo("password",user.getPassword());
            }
            // ע���ֻ���
            if(!StringUtils.isEmpty(user.getPhone())){
                    criteria.andEqualTo("phone",user.getPhone());
            }
            // ע������
            if(!StringUtils.isEmpty(user.getEmail())){
                    criteria.andEqualTo("email",user.getEmail());
            }
            // ����ʱ��
            if(!StringUtils.isEmpty(user.getCreated())){
                    criteria.andEqualTo("created",user.getCreated());
            }
            // �޸�ʱ��
            if(!StringUtils.isEmpty(user.getUpdated())){
                    criteria.andEqualTo("updated",user.getUpdated());
            }
            // ��Ա��Դ��1:PC��2��H5��3��Android��4��IOS
            if(!StringUtils.isEmpty(user.getSourceType())){
                    criteria.andEqualTo("sourceType",user.getSourceType());
            }
            // �ǳ�
            if(!StringUtils.isEmpty(user.getNickName())){
                    criteria.andEqualTo("nickName",user.getNickName());
            }
            // ��ʵ����
            if(!StringUtils.isEmpty(user.getName())){
                    criteria.andLike("name","%"+user.getName()+"%");
            }
            // ʹ��״̬��1���� 0��������
            if(!StringUtils.isEmpty(user.getStatus())){
                    criteria.andEqualTo("status",user.getStatus());
            }
            // ͷ���ַ
            if(!StringUtils.isEmpty(user.getHeadPic())){
                    criteria.andEqualTo("headPic",user.getHeadPic());
            }
            // QQ����
            if(!StringUtils.isEmpty(user.getQq())){
                    criteria.andEqualTo("qq",user.getQq());
            }
            // �ֻ��Ƿ���֤ ��0��  1�ǣ�
            if(!StringUtils.isEmpty(user.getIsMobileCheck())){
                    criteria.andEqualTo("isMobileCheck",user.getIsMobileCheck());
            }
            // �����Ƿ��⣨0��  1�ǣ�
            if(!StringUtils.isEmpty(user.getIsEmailCheck())){
                    criteria.andEqualTo("isEmailCheck",user.getIsEmailCheck());
            }
            // �Ա�1�У�0Ů
            if(!StringUtils.isEmpty(user.getSex())){
                    criteria.andEqualTo("sex",user.getSex());
            }
            // ��Ա�ȼ�
            if(!StringUtils.isEmpty(user.getUserLevel())){
                    criteria.andEqualTo("userLevel",user.getUserLevel());
            }
            // ����
            if(!StringUtils.isEmpty(user.getPoints())){
                    criteria.andEqualTo("points",user.getPoints());
            }
            // ����ֵ
            if(!StringUtils.isEmpty(user.getExperienceValue())){
                    criteria.andEqualTo("experienceValue",user.getExperienceValue());
            }
            // ����������
            if(!StringUtils.isEmpty(user.getBirthday())){
                    criteria.andEqualTo("birthday",user.getBirthday());
            }
            // ����¼ʱ��
            if(!StringUtils.isEmpty(user.getLastLoginTime())){
                    criteria.andEqualTo("lastLoginTime",user.getLastLoginTime());
            }
        }
        return example;
    }

    /**
     * ɾ��
     * @param id
     */
    @Override
    public void delete(String id){
        userMapper.deleteByPrimaryKey(id);
    }

    /**
     * �޸�User
     * @param user
     */
    @Override
    public void update(User user){
        userMapper.updateByPrimaryKey(user);
    }

    /**
     * ����User
     * @param user
     */
    @Override
    public void add(User user){
        userMapper.insert(user);
    }

    /**
     * ����ID��ѯUser
     * @param id
     * @return
     */
    @Override
    public User findById(String id){
        return  userMapper.selectByPrimaryKey(id);
    }

    /**
     * ��ѯUserȫ������
     * @return
     */
    @Override
    public List<User> findAll() {
        return userMapper.selectAll();
    }

    @Override
    public int addPoints(String username, Integer points) {
        return userMapper.addPoints(username,points);
    }
}
