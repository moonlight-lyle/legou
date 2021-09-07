package com.changgou.user.service;
import com.changgou.user.pojo.UndoLog;
import com.github.pagehelper.PageInfo;
import java.util.List;
/****
 * @Author:admin
 * @Description:UndoLogҵ���ӿ�
 * @Date 2019/6/14 0:16
 *****/
public interface UndoLogService {

    /***
     * UndoLog��������ҳ��ѯ
     * @param undoLog
     * @param page
     * @param size
     * @return
     */
    PageInfo<UndoLog> findPage(UndoLog undoLog, int page, int size);

    /***
     * UndoLog��ҳ��ѯ
     * @param page
     * @param size
     * @return
     */
    PageInfo<UndoLog> findPage(int page, int size);

    /***
     * UndoLog��������������
     * @param undoLog
     * @return
     */
    List<UndoLog> findList(UndoLog undoLog);

    /***
     * ɾ��UndoLog
     * @param id
     */
    void delete(Long id);

    /***
     * �޸�UndoLog����
     * @param undoLog
     */
    void update(UndoLog undoLog);

    /***
     * ����UndoLog
     * @param undoLog
     */
    void add(UndoLog undoLog);

    /**
     * ����ID��ѯUndoLog
     * @param id
     * @return
     */
     UndoLog findById(Long id);

    /***
     * ��ѯ����UndoLog
     * @return
     */
    List<UndoLog> findAll();
}
