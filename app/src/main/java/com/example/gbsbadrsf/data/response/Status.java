package com.example.gbsbadrsf.data.response;

public enum Status {
    IDLE(500),
    LOADING(0),
    SUCCESS(1),
    ERROR(2);
    int Status;

    Status(int status) {
        Status = status;
    }

    public int getStatus() {
        return Status;
    }
}

