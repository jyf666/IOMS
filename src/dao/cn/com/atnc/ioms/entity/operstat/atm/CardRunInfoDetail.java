/* Copyright  2011 BEIJING ATNC CO,.LTD
 * All rights reserved
 *
 * create at Mar 7, 2011 3:32:07 PM
 * author:wangpeng E-mail:wangpeng@atnc.com.cn
 */

package cn.com.atnc.ioms.entity.operstat.atm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.com.atnc.common.entity.StringUUIDEntity;
import cn.com.atnc.ioms.entity.basedata.atm.node.AtmNode;
import cn.com.atnc.ioms.enums.operstat.atm.EnumCollections.ATMCardRunInfoType;

/**
 * 板卡运行信息详情实体类
 * 
 * @author wangpeng
 * @date:Mar 7, 2011 3:32:07 PM
 * @version 1.0
 */
@Entity
@Table(name = "TB_BD_ATM_CARD_RUNINFO_DETAIL")
public class CardRunInfoDetail extends StringUUIDEntity {

	/***************************************************************************
	 * serialVersionUID:
	 */
	private static final long serialVersionUID = 2472879404347816414L;
	private AtmNode node;
	private Card card;
	private String cardSlot;
	private ATMCardRunInfoType cardRunInfoType;
	private int cardCriticalSum;
	private int cardMajorSum;
	private int cardMinorSum;
	private Boolean isAlarm;

	/**
	 * @return the node
	 */
	@ManyToOne
	@JoinColumn(name = "TB_NODE_ID")
	public AtmNode getNode() {
		return node;
	}

	public void setNode(AtmNode node) {
		this.node = node;
	}

	/**
	 * @return the card
	 */
	@ManyToOne
	@JoinColumn(name = "TB_CARD_ID")
	public Card getCard() {
		return card;
	}

	/**
	 * @param card
	 *            the card to set
	 */
	public void setCard(Card card) {
		this.card = card;
	}

	/**
	 * @return the cardSlot
	 */
	@Column(name = "CARD_SLOT")
	public String getCardSlot() {
		return cardSlot;
	}

	/**
	 * @param cardSlot
	 *            the cardSlot to set
	 */
	public void setCardSlot(String cardSlot) {
		this.cardSlot = cardSlot;
	}

	/**
	 * @return the cardRunInfoType
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "CARD_ALARM_TYPE")
	public ATMCardRunInfoType getCardRunInfoType() {
		return cardRunInfoType;
	}

	/**
	 * @param cardRunInfoType
	 *            the cardRunInfoType to set
	 */
	public void setCardRunInfoType(ATMCardRunInfoType cardRunInfoType) {
		this.cardRunInfoType = cardRunInfoType;
	}

	/**
	 * @return the cardCriticalSum
	 */
	@Column(name = "CARD_CRITICAL_SUM")
	public int getCardCriticalSum() {
		return cardCriticalSum;
	}

	/**
	 * @param cardCriticalSum
	 *            the cardCriticalSum to set
	 */
	public void setCardCriticalSum(int cardCriticalSum) {
		this.cardCriticalSum = cardCriticalSum;
	}

	/**
	 * @return the cardMajorSum
	 */
	@Column(name = "CARD_MAJOR_SUM")
	public int getCardMajorSum() {
		return cardMajorSum;
	}

	/**
	 * @param cardMajorSum
	 *            the cardMajorSum to set
	 */
	public void setCardMajorSum(int cardMajorSum) {
		this.cardMajorSum = cardMajorSum;
	}

	/**
	 * @return the cardMinorSum
	 */
	@Column(name = "CARD_MINOR_SUM")
	public int getCardMinorSum() {
		return cardMinorSum;
	}

	/**
	 * @param cardMinorSum
	 *            the cardMinorSum to set
	 */
	public void setCardMinorSum(int cardMinorSum) {
		this.cardMinorSum = cardMinorSum;
	}

	public Boolean getIsAlarm() {
		return isAlarm;
	}

	public void setIsAlarm(Boolean isAlarm) {
		this.isAlarm = isAlarm;
	}

}
