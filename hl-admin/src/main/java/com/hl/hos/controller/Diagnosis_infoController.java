package com.hl.hos.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hl.hos.backPogo.DiagnosisDoctorHos;
import com.hl.hos.backPogo.DoctorHos;
import com.hl.hos.pojo.Disgnose_info;
import com.hl.hos.pojo.Doctor_info;
import com.hl.hos.pojo.Hos_info;
import com.hl.hos.pojo.Result;
import com.hl.hos.service.Disgnose_infoService;
import com.hl.hos.service.Doctor_infoService;
import com.hl.hos.service.Hos_infoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 诊断信息页面
 * @author 何夜息
 * @since 2021-07-08
 */
@RestController
@RequestMapping("/hos/diagnosis_info")
public class Diagnosis_infoController
{
    @Autowired
    private Doctor_infoService doctor_infoService;
    @Autowired
    private Disgnose_infoService disgnose_infoService;
    @Autowired
    private Hos_infoService hos_infoService;
    @Autowired
    private Result result;

    /**
     * 查询诊断信息
     * @return
     */
    @ResponseBody
    @GetMapping("/get_diagnosis_list")
    public Result get_diagnosis_list(String page,String limit)
    {
        List<Disgnose_info> list = disgnose_infoService.list();
        List<DiagnosisDoctorHos> resList = new ArrayList<DiagnosisDoctorHos>();

        Page<Disgnose_info> page1 = new Page<Disgnose_info>(Integer.parseInt(page),Integer.parseInt(limit));
        IPage<Disgnose_info> iPage = disgnose_infoService.page(page1);

        for (int i = 0; i < iPage.getRecords().size(); i++)
        {
            DiagnosisDoctorHos diagnosisDoctorHos = new DiagnosisDoctorHos();
            Disgnose_info disgnose_info = iPage.getRecords().get(i);

            diagnosisDoctorHos.setDisgnose_info(disgnose_info);//绑定诊断信息
            Doctor_info doctorInfo = doctor_infoService.getById(disgnose_info.getDoctor_id());
            doctorInfo.setDoctor_pwd(null);
            diagnosisDoctorHos.setDoctor_info(doctorInfo);//绑定医生
            Long hos_id = doctor_infoService.getById(disgnose_info.getDoctor_id()).getHos_id();
            diagnosisDoctorHos.setHos_info(hos_infoService.getById(hos_id));//绑定医院信息

            resList.add(diagnosisDoctorHos);
        }
        result.setCount(list.size());//数量应该是所有数据的大小
        result.setData(resList);
        result.setCode(200);
        return result;
    }

    /**
     * 根据诊断编号查询数据
     * @return
     */
    @ResponseBody
    @GetMapping("/get_diagnosis_by_id")
    public Result get_diagnosis_by_id(Long id)
    {
        List<Disgnose_info> list = disgnose_infoService.list(new QueryWrapper<Disgnose_info>()
                .eq("id",id)
        );
        List<DiagnosisDoctorHos> resList = new ArrayList<DiagnosisDoctorHos>();

        for (int i = 0; i < list.size(); i++)
        {
            DiagnosisDoctorHos diagnosisDoctorHos = new DiagnosisDoctorHos();
            Disgnose_info disgnose_info = list.get(i);

            diagnosisDoctorHos.setDisgnose_info(disgnose_info);//绑定诊断信息
            Doctor_info doctorInfo = doctor_infoService.getById(disgnose_info.getDoctor_id());
            diagnosisDoctorHos.setDoctor_info(doctorInfo);//绑定医生
            Long hos_id = doctor_infoService.getById(disgnose_info.getDoctor_id()).getHos_id();
            diagnosisDoctorHos.setHos_info(hos_infoService.getById(hos_id));//绑定医院信息
            doctorInfo.setDoctor_pwd(null);
            resList.add(diagnosisDoctorHos);
        }
        result.setCount(list.size());//数量应该是所有数据的大小
        result.setData(resList);
        result.setCode(200);
        return result;
    }

    /**
     * 更新诊断信息
     * @return
     */
    @ResponseBody
    @PostMapping("/update_diagnosis_info")
    public Result update_diagnosis_info(Disgnose_info disgnose_info)
    {
        if(disgnose_infoService.updateById(disgnose_info))
        {
            result.setCount(1);
            result.setData(null);
            result.setCode(200);
            result.setMsg("修改成功!");
        }else {
            result.setCount(1);
            result.setData(null);
            result.setCode(201);
            result.setMsg("修改失败!");
        }
        return result;
    }


}

