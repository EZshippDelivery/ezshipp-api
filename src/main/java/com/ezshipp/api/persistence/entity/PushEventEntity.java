package com.ezshipp.api.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "push_event",  catalog = "")
@Setter
@Getter
public class PushEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "event_id", nullable = false)
    private int eventId;
    @Column(name = "multicast_id", length = 30)
    private String multiCastId;
    @Column(name = "success")
    private boolean success;
    @Column(name = "failure")
    private boolean failure;
    @Column(name = "type")
    private String type;
    @Column(name = "result", length = 300)
    private String result;
}