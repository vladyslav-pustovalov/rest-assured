package com.free.pet.petmodels;

public enum Status {
        AVAILABLE("available"),
        PENDING("pending"),
        SOLD("sold");

        public final String status;

        private Status(String status) {
                this.status = status;
        }
}
