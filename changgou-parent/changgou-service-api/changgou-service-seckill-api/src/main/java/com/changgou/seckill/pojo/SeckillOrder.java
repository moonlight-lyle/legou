package com.changgou.seckill.pojo;
import javax.persistence.*;
import java.io.Serializable;
import java.lang.Long;
import java.util.Date;
import java.lang.String;

@Table(name="tb_seckill_order")
public class SeckillOrder implements Serializable{

	@Id
    @Column(name = "id")
	private Long id;

    @Column(name = "seckill_id")
	private Long seckillId;

    @Column(name = "money")
	private String money;

    @Column(name = "user_id")
	private String userId;

    @Column(name = "create_time")
	private Date createTime;

    @Column(name = "pay_time")
	private Date payTime;

    @Column(name = "status")
	private String status;

    @Column(name = "receiver_address")
	private String receiverAddress;

    @Column(name = "receiver_mobile")
	private String receiverMobile;

    @Column(name = "receiver")
	private String receiver;

    @Column(name = "transaction_id")
	private String transactionId;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(Long seckillId) {
		this.seckillId = seckillId;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public String getReceiverMobile() {
		return receiverMobile;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}


}
