package com.cardinity.taskmanager.service;

/**
 * @author ahadahmed
 * @see <a href="#"> see this</a>
 */
public class InvalidTaskException extends RuntimeException {

    public InvalidTaskException(String reason) {
        super(reason);
    }
}
