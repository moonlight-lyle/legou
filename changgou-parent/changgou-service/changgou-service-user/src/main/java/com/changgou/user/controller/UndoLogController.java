package com.changgou.user.controller;
import com.changgou.user.pojo.UndoLog;
import com.changgou.user.service.UndoLogService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/****
 * @Author:admin
 * @Description:
 * @Date 2019/6/14 0:18
 *****/

@RestController
@RequestMapping("/undoLog")
@CrossOrigin
public class UndoLogController {

    @Autowired
    private UndoLogService undoLogService;

    /***
     * UndoLog��ҳ��������ʵ��
     * @param undoLog
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false)  UndoLog undoLog, @PathVariable  int page, @PathVariable  int size){
        //����UndoLogServiceʵ�ַ�ҳ������ѯUndoLog
        PageInfo<UndoLog> pageInfo = undoLogService.findPage(undoLog, page, size);
        return new Result(true,StatusCode.OK,"��ѯ�ɹ�",pageInfo);
    }

    /***
     * UndoLog��ҳ����ʵ��
     * @param page:��ǰҳ
     * @param size:ÿҳ��ʾ������
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size){
        //����UndoLogServiceʵ�ַ�ҳ��ѯUndoLog
        PageInfo<UndoLog> pageInfo = undoLogService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"��ѯ�ɹ�",pageInfo);
    }

    /***
     * ����������Ʒ������
     * @param undoLog
     * @return
     */
    @PostMapping(value = "/search" )
    public Result<List<UndoLog>> findList(@RequestBody(required = false)  UndoLog undoLog){
        //����UndoLogServiceʵ��������ѯUndoLog
        List<UndoLog> list = undoLogService.findList(undoLog);
        return new Result<List<UndoLog>>(true,StatusCode.OK,"��ѯ�ɹ�",list);
    }

    /***
     * ����IDɾ��Ʒ������
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Long id){
        //����UndoLogServiceʵ�ָ�������ɾ��
        undoLogService.delete(id);
        return new Result(true,StatusCode.OK,"ɾ���ɹ�");
    }

    /***
     * �޸�UndoLog����
     * @param undoLog
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody  UndoLog undoLog,@PathVariable Long id){
        //��������ֵ
        undoLog.setId(id);
        //����UndoLogServiceʵ���޸�UndoLog
        undoLogService.update(undoLog);
        return new Result(true,StatusCode.OK,"�޸ĳɹ�");
    }

    /***
     * ����UndoLog����
     * @param undoLog
     * @return
     */
    @PostMapping
    public Result add(@RequestBody   UndoLog undoLog){
        //����UndoLogServiceʵ�����UndoLog
        undoLogService.add(undoLog);
        return new Result(true,StatusCode.OK,"��ӳɹ�");
    }

    /***
     * ����ID��ѯUndoLog����
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<UndoLog> findById(@PathVariable Long id){
        //����UndoLogServiceʵ�ָ���������ѯUndoLog
        UndoLog undoLog = undoLogService.findById(id);
        return new Result<UndoLog>(true,StatusCode.OK,"��ѯ�ɹ�",undoLog);
    }

    /***
     * ��ѯUndoLogȫ������
     * @return
     */
    @GetMapping
    public Result<List<UndoLog>> findAll(){
        //����UndoLogServiceʵ�ֲ�ѯ����UndoLog
        List<UndoLog> list = undoLogService.findAll();
        return new Result<List<UndoLog>>(true, StatusCode.OK,"��ѯ�ɹ�",list) ;
    }
}
