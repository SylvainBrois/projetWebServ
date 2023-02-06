package com.example.projetwebserv.service

import org.springframework.stereotype.Service
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Service
class TaskService {
    fun execute(task: String) {
        logger.info("do $task")
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(TaskService::class.java)
    }

}