package com.changgou.user.service.impl;
import com.changgou.user.dao.UndoLogMapper;
import com.changgou.user.pojo.UndoLog;
import com.changgou.user.service.UndoLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import java.util.List;
/****
 * @Author:admin
 * @Description:UndoLogҵ���ӿ�ʵ����
 * @Date 2019/6/14 0:16
 *****/
@Service
public class UndoLogServiceImpl implements UndoLogService {

    @Autowired
    private UndoLogMapper undoLogMapper;


    /**
     * UndoLog����+��ҳ��ѯ
     * @param undoLog ��ѯ����
     * @param page ҳ��
     * @param size ҳ��С
     * @return ��ҳ���
     */
    @Override
    public PageInfo<UndoLog> findPage(UndoLog undoLog, int page, int size){
        //��ҳ
        PageHelper.startPage(page,size);
        //������������
        Example example = createExample(undoLog);
        //ִ������
        return new PageInfo<UndoLog>(undoLogMapper.selectByExample(example));
    }

    /**
     * UndoLog��ҳ��ѯ
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<UndoLog> findPage(int page, int size){
        //��̬��ҳ
        PageHelper.startPage(page,size);
        //��ҳ��ѯ
        return new PageInfo<UndoLog>(undoLogMapper.selectAll());
    }

    /**
     * UndoLog������ѯ
     * @param undoLog
     * @return
     */
    @Override
    public List<UndoLog> findList(UndoLog undoLog){
        //������ѯ����
        Example example = createExample(undoLog);
        //���ݹ�����������ѯ����
        return undoLogMapper.selectByExample(example);
    }


    /**
     * UndoLog������ѯ����
     * @param undoLog
     * @return
     */
    public Example createExample(UndoLog undoLog){
        Example example=new Example(UndoLog.class);
        Example.Criteria criteria = example.createCriteria();
        if(undoLog!=null){
            // 
            if(!StringUtils.isEmpty(undoLog.getId())){
                    criteria.andEqualTo("id",undoLog.getId());
            }
            // 
            if(!StringUtils.isEmpty(undoLog.getBranchId())){
                    criteria.andEqualTo("branchId",undoLog.getBranchId());
            }
            // 
            if(!StringUtils.isEmpty(undoLog.getXid())){
                    criteria.andEqualTo("xid",undoLog.getXid());
            }
            // 
            if(!StringUtils.isEmpty(undoLog.getRollbackInfo())){
                    criteria.andEqualTo("rollbackInfo",undoLog.getRollbackInfo());
            }
            // 
            if(!StringUtils.isEmpty(undoLog.getLogStatus())){
                    criteria.andEqualTo("logStatus",undoLog.getLogStatus());
            }
            // 
            if(!StringUtils.isEmpty(undoLog.getLogCreated())){
                    criteria.andEqualTo("logCreated",undoLog.getLogCreated());
            }
            // 
            if(!StringUtils.isEmpty(undoLog.getLogModified())){
                    criteria.andEqualTo("logModified",undoLog.getLogModified());
            }
            // 
            if(!StringUtils.isEmpty(undoLog.getExt())){
                    criteria.andEqualTo("ext",undoLog.getExt());
            }
        }
        return example;
    }

    /**
     * ɾ��
     * @param id
     */
    @Override
    public void delete(Long id){
        undoLogMapper.deleteByPrimaryKey(id);
    }

    /**
     * �޸�UndoLog
     * @param undoLog
     */
    @Override
    public void update(UndoLog undoLog){
        undoLogMapper.updateByPrimaryKey(undoLog);
    }

    /**
     * ����UndoLog
     * @param undoLog
     */
    @Override
    public void add(UndoLog undoLog){
        undoLogMapper.insert(undoLog);
    }

    /**
     * ����ID��ѯUndoLog
     * @param id
     * @return
     */
    @Override
    public UndoLog findById(Long id){
        return  undoLogMapper.selectByPrimaryKey(id);
    }

    /**
     * ��ѯUndoLogȫ������
     * @return
     */
    @Override
    public List<UndoLog> findAll() {
        return undoLogMapper.selectAll();
    }
}
