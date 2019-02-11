package com.penghuang.blog.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 邀请码实体类，(注册页面只有输入邀请码才能注册成功)
 * @author penghuang
 *
 */
@Entity // 实体
public class InvitationCode implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id // 主键
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
	private Long id; // 邀请码的唯一标识
	
	@NotEmpty(message = "邀请码不能为空")
	@Size(min=4, max=4)
	@Column(nullable = false,length = 4) // 映射为字段，值不能为空,且只能为4位数
	private String code;
	
	@NotEmpty(message = "邀请码状态不能为空")
	@Size(min=1, max=1)
	@Column(nullable = false) // 邀请码状态,为1可用,为0则已被使用
	private String status;//若是boolean类型则在mysql数据库中会被映射成tinyint类型,1为true;0为false

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
 
}
