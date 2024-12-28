CREATE TABLE `users` (
    `user_id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(20) NULL,
    `id` varchar(20) NULL,
    `password` varchar(20) NULL,
    `phone_number` varchar(20) NULL,
    `email` varchar(20) NULL,
    `withdrawn` varchar(20) NULL DEFAULT 'N',
    `point_balance` decimal NULL,
     PRIMARY KEY (`user_id`),
     UNIQUE KEY (`id`),
     UNIQUE KEY (`email`)
);


CREATE TABLE `point` (
    `point_id` int NOT NULL AUTO_INCREMENT,
    `amount` decimal NULL,
    `reason` varchar(255) NULL,
     PRIMARY KEY (`point_id`)
);

CREATE TABLE `coupon` (
    `coupon_id` int NOT NULL AUTO_INCREMENT,
    `coupon_name` varchar(20) NULL,
    `discount_rate` int NULL,
     PRIMARY KEY (`coupon_id`)
);


CREATE TABLE `user_coupon` (
    `user_coupon_id` int NOT NULL AUTO_INCREMENT,
    `used_status` int NULL,
    `user_id` int NOT NULL,
     PRIMARY KEY (`user_coupon_id`),
     FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`)
);

CREATE TABLE `delivery_address` (
    `delivery_address_id` int NOT NULL AUTO_INCREMENT,
    `delivery_name` varchar(20) NULL,
    `postal_code` varchar(20) NULL,
    `address` varchar(20) NULL,
    `detail_address` varchar(90) NULL,
    `default_address_status` int NULL,
    `recipient` varchar(20) NULL,
    `recipient_contact` varchar(20) NULL,
    `delivery_request` varchar(20) NULL,
    `user_id` int NOT NULL,
     PRIMARY KEY (`delivery_address_id`),
     FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`)
);

CREATE TABLE `inquiries` (
    `inquiry_id` int NOT NULL AUTO_INCREMENT,
    `title` varchar(20) NULL,
    `content` varchar(255) NULL,
    `created_at` DATE NULL,
    `updated_at` DATE NULL,
    `is_private` int NULL,
    `view_count` int NULL,
    `has_reply` varchar(20) NULL,
    `user_id` int NOT NULL,
    PRIMARY KEY (`inquiry_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`)
);

CREATE TABLE `categories` (
    `category_id` int NOT NULL AUTO_INCREMENT,
    `category_name` varchar(20) NULL,
    `parent_category_id` int NULL,
    PRIMARY KEY (`category_id`),
    FOREIGN KEY (`parent_category_id`) REFERENCES `categories`(`category_id`)
);

CREATE TABLE `cart` (
    `cart_id` int NOT NULL AUTO_INCREMENT,
    `user_id` int NOT NULL,
    PRIMARY KEY (`cart_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`)
);

CREATE TABLE `products` (
    `product_id` int NOT NULL AUTO_INCREMENT,
    `product_name` varchar(20) NULL,
    `price` decimal NULL,
    `discount_price` decimal NULL,
    `detail` varchar(255) NULL,
    `discount_rate` int NULL,
    `discount_period` Date NULL,
    `size_guide` varchar(90) NULL,
    `registered_date` Date NULL,
    `modified_date` Date NULL,
    `sale_status` varchar(20) NULL,
    `is_deleted` int NULL,
    `cart_id` int NOT NULL,
    PRIMARY KEY (`product_id`),
    FOREIGN KEY (`cart_id`) REFERENCES `cart`(`cart_id`)
    );

CREATE TABLE `category_products` (
    `category_id` int NOT NULL,
    `product_id` int NOT NULL,
    PRIMARY KEY (`category_id`, `product_id`),
    FOREIGN KEY (`category_id`) REFERENCES `categories`(`category_id`),
    FOREIGN KEY (`product_id`) REFERENCES `products`(`product_id`)
);



CREATE TABLE `product_colors` (
    `product_color_id` int NOT NULL AUTO_INCREMENT,
    `coordinated_product_id` int NULL,
    `color` varchar(20) NULL,
    `thumbnail1` varchar(255) NULL,
    `thumbnail2` varchar(255) NULL,
    `thumbnail_by_color` varchar(255) NULL,
    `product_id` int NOT NULL,
    PRIMARY KEY (`product_color_id`),
    FOREIGN KEY (`product_id`) REFERENCES `products`(`product_id`)
);

CREATE TABLE `sizes` (
    `size_id` int NOT NULL AUTO_INCREMENT,
    `size` varchar(20) NULL,
    `stock` int NULL,
    `product_color_id` int NOT NULL,
    PRIMARY KEY (`size_id`),
    FOREIGN KEY (`product_color_id`) REFERENCES `product_colors`(`product_color_id`)
);

CREATE TABLE `product_images` (
    `product_image_id` int NOT NULL AUTO_INCREMENT,
    `image_url` varchar(255) NULL,
    `file_size` int NULL,
    `product_color_id` int NOT NULL,
    `extension` varchar(20) NULL,
    `image_created_date` Date NULL,
    PRIMARY KEY (`product_image_id`),
    FOREIGN KEY (`product_color_id`) REFERENCES `product_colors`(`product_color_id`)
);

CREATE TABLE `coordinate_products` (
    `coordinate_id` int NOT NULL AUTO_INCREMENT,
    `original_product_id` int NOT NULL,
    `coordinated_product_id` int NOT NULL,
    PRIMARY KEY (`coordinate_id`),
    FOREIGN KEY (`original_product_id`) REFERENCES `products`(`product_id`),
    FOREIGN KEY (`coordinated_product_id`) REFERENCES `products`(`product_id`)
    );

CREATE TABLE `orders` (
    `order_id` int NOT NULL AUTO_INCREMENT,
    `order_name` varchar(20) NULL,
    `order_contact` varchar(20) NULL,
    `order_email` varchar(20) NULL,
    `total_order_quantity` int NULL,
    `total_payment_amount` decimal NULL,
    `order_date` Date NULL,
    `recipient_name` varchar(50) NULL,
    `recipient_contact` varchar(50) NULL,
    `postal_code` varchar(20) NULL,
    `address` varchar(20) NULL,
    `detailed_address` varchar(255) NULL,
    `point_usage` decimal NULL,
    `order_number` int NULL,
    `user_id` int NOT NULL,
    `delivery_id` int NOT NULL,
    PRIMARY KEY (`order_id`),
    UNIQUE (`order_number`),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`),
    FOREIGN KEY (`delivery_id`) REFERENCES `delivery_address`(`delivery_address_id`)
    );


CREATE TABLE `order_products` (
    `order_product_id` int NOT NULL AUTO_INCREMENT,
    `size` varchar(20) NULL,
    `quantity` int NULL,
    `order_status` varchar(20) NULL,
    `coupon_discount` decimal NULL,
    `claim_status` varchar(20) NULL,
    `buy_confirm_date` Date NULL,
    `point_discount` decimal NULL,
    `product_id` int NOT NULL,
    `order_id` int NOT NULL,
    PRIMARY KEY (`order_product_id`),
    FOREIGN KEY (`product_id`) REFERENCES `products`(`product_id`),
    FOREIGN KEY (`order_id`) REFERENCES `orders`(`order_id`)
    );


CREATE TABLE `reviews` (
    `review_id` int NOT NULL AUTO_INCREMENT,
    `created_date` Date NULL,
    `modified_date` Date NULL,
    `rating` int NULL,
    `review` varchar(255) NULL,
    `review_status` varchar(20) NULL,
    `user_id` int NOT NULL,
    `order_product_id` int NOT NULL,
    PRIMARY KEY (`review_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`),
    FOREIGN KEY (`order_product_id`) REFERENCES `order_products`(`order_product_id`)
    );

CREATE TABLE `review_images` (
    `review_image_id` int NOT NULL AUTO_INCREMENT,
    `review_image_url` varchar(255) NULL,
    `file_size` int NULL,
    `extension` varchar(20) NULL,
    `image_created_date` Date NULL,
    `review_id` int NOT NULL,
    PRIMARY KEY (`review_image_id`),
    FOREIGN KEY (`review_id`) REFERENCES `reviews`(`review_id`)
);

CREATE TABLE `payments` (
    `payment_id` int NOT NULL NOT NULL AUTO_INCREMENT,
    `payment_method` varchar(20) NULL,
    `payment_date` Date NULL,
    `total_order_amount` decimal NULL,
    `total_payment_status` decimal NULL,
    `payment_status` varchar(20) NULL,
    `order_id` int NOT NULL,
    PRIMARY KEY (`payment_id`),
    FOREIGN KEY (`order_id`) REFERENCES `orders`(`order_id`)
);

CREATE TABLE `bank_transfer_payments` (
    `bank_transfer_id` int NOT NULL NOT NULL AUTO_INCREMENT,
    `deposit_amount` decimal NULL,
    `depositor_name` varchar(20) NULL,
    `deposit_bank` varchar(20) NULL,
    `deposit_date` DATE NULL,
    `payment_id` int NOT NULL,
    PRIMARY KEY (`bank_transfer_id`),
    FOREIGN KEY (`payment_id`) REFERENCES `payments`(`payment_id`)
);


CREATE TABLE `delivery_info` (
    `delivery_id` int NOT NULL NOT NULL AUTO_INCREMENT,
    `courier` varchar(20) NULL,
    `invoice_number` int NULL,
    `tracking_delivery` varchar(255) NULL,
    `delivery_process_date` Date NULL,
    `delivery_complete_date` Date NULL,
    `delivery_fee` varchar(20) NULL,
    `order_product_id` int NOT NULL,
    `delivery_address_id` int NOT NULL,
     PRIMARY KEY (`delivery_id`),
     FOREIGN KEY (`delivery_address_id`) REFERENCES `delivery_address`(`delivery_address_id`)
);

CREATE TABLE `canceled_products` (
    `canceled_product_id` int NOT NULL NOT NULL AUTO_INCREMENT,
    `cancellation_request_date` Date NULL,
    `cancellation_status` varchar(20) NULL,
    `cancellation_approval_date` Date NULL,
    `order_product_id` int NOT NULL,
     PRIMARY KEY (`canceled_product_id`),
     FOREIGN KEY (`order_product_id`) REFERENCES `order_products`(`order_product_id`)
);

CREATE TABLE `returns` (
    `return_id` int NOT NULL NOT NULL AUTO_INCREMENT,
    `return_status` varchar(20) NULL,
    `pickup_method` varchar(20) NULL,
    `return_request_date` Date NULL,
    `return_complete_date` Date NULL,
    `return_reason` varchar(225) NULL,
    `order_product_id` int NOT NULL,
    PRIMARY KEY (`return_id`),
    FOREIGN KEY (`order_product_id`) REFERENCES `order_products`(`order_product_id`)
);

CREATE TABLE `exchange` (
    `exchange_id` int NOT NULL AUTO_INCREMENT,
    `exchange_status` varchar(20) NULL,
    `exchange_reason` varchar(20) NULL,
    `exchange_detail_reason` varchar(255) NULL,
    `exchange_requested_option` varchar(20) NULL,
    `exchange_requested_size` varchar(20) NULL,
    `exchange_request_date` Date NULL,
    `collection_method` varchar(20) NULL,
    `order_product_id` int NOT NULL,
    PRIMARY KEY (`exchange_id`),
    FOREIGN KEY (`order_product_id`) REFERENCES `order_products`(`order_product_id`)
    );


CREATE TABLE `exchange_delivery` (
    `exchange_delivery_id` int NOT NULL NOT NULL AUTO_INCREMENT,
    `pickup_courier` varchar(20) NULL,
    `invoice number` varchar(20) NULL,
    `resend_courier` varchar(20) NULL,
    `exchange_invoice_number` int NULL,
    `redelivery_date` Date NULL,
    `tracking _delivery` varchar(255) NULL,
    `pickup_complete_date` Date NULL,
    `exchange_delivery_fee` decimal NULL,
    `pickup_status` varchar(20) NULL,
    `exchange_id` int NOT NULL,
    `delivery_address_id` int NOT NULL,
     PRIMARY KEY (`exchange_delivery_id`),
     FOREIGN KEY (`exchange_id`) REFERENCES `exchange`(`exchange_id`),
     FOREIGN KEY (`delivery_address_id`) REFERENCES `delivery_address`(`delivery_address_id`)
);

CREATE TABLE `refunds` (
    `refund_id` int NOT NULL NOT NULL AUTO_INCREMENT,
    `refund_reason` varchar(20) NULL,
    `refund_detail` varchar(255) NULL,
    `refund_status` varchar(20) NULL,
    `refund_complete_date` Date NULL,
    `refund_amount` decimal NULL,
    `order_product_id` int NOT NULL,
    PRIMARY KEY (`refund_id`),
    FOREIGN KEY (`order_product_id`) REFERENCES `order_products`(`order_product_id`)
    );

CREATE TABLE `inquiry_comments` (
    `comment_id` int NOT NULL NOT NULL AUTO_INCREMENT,
    `reply_content` varchar(255) NULL,
    `reply_date` Date NULL,
    `reply_modified_date` Date NULL,
    `inquiry_id` int NOT NULL,
    PRIMARY KEY (`comment_id`),
    FOREIGN KEY (`inquiry_id`) REFERENCES `inquiries`(`inquiry_id`)
);

CREATE TABLE `disruptive_customers` (
    `disruptive_customer_id` int NOT NULL NOT NULL AUTO_INCREMENT,
    `restriction_reason` varchar(255) NULL,
    `restriction_status` varchar(20) NULL,
    `user_id` int NOT NULL,
    PRIMARY KEY (`disruptive_customer_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`)
);

CREATE TABLE `notices` (
    `notice_id` int NOT NULL NOT NULL AUTO_INCREMENT,
    `title` varchar(20) NULL,
    `content` varchar(255) NULL,
    `created_date` DATE NULL,
    `modified_date` Date NULL,
    `popup_status` int NULL,
    PRIMARY KEY (`notice_id`)
);

CREATE TABLE `notice_images` (
     `notice_image_id` int NOT NULL NOT NULL AUTO_INCREMENT,
     `image_url` varchar(255) NULL,
     `file_size` int NULL,
     `extension` varchar(20) NULL,
     `image_creation_date` Date NULL,
     `notice_id` int NOT NULL,
     PRIMARY KEY (`notice_image_id`),
     FOREIGN KEY (`notice_id`) REFERENCES `notices`(`notice_id`)
);

CREATE TABLE `magazines` (
     `magazine_id` int NOT NULL NOT NULL AUTO_INCREMENT,
     `title` varchar(20) NULL,
     `content` varchar(255) NULL,
     `created_date` DATE NULL,
     `modified_date` Date NULL,
     `view_count` int NULL,
     `main_image` varchar(255) NULL,
     PRIMARY KEY (`magazine_id`)
);

CREATE TABLE `magazine_images` (
    `magazine_image_id` int NOT NULL AUTO_INCREMENT,
    `image_url` varchar(255) NULL,
    `file_size` int NULL,
    `extension` varchar(20) NULL,
    `creation_date` Date NULL,
    `magazine_id` int NOT NULL,
    PRIMARY KEY (`magazine_image_id`),
    FOREIGN KEY (`magazine_id`) REFERENCES `magazines`(`magazine_id`)
);

CREATE TABLE `cart_products` (
    `cart_id` int NOT NULL AUTO_INCREMENT,
    `product_id` int NOT NULL,
    `product_quantity` int NULL,
    `size` varchar(20) NULL,
    PRIMARY KEY (`cart_id`, `product_id`),
    FOREIGN KEY (`cart_id`) REFERENCES `cart`(`cart_id`),
    FOREIGN KEY (`product_id`) REFERENCES `products`(`product_id`)
);

CREATE TABLE `order_coupons` (
    `order_id` int NOT NULL AUTO_INCREMENT,
    `user_coupon_id` int NOT NULL,
    PRIMARY KEY (`order_id`, `user_coupon_id`),
    FOREIGN KEY (`order_id`) REFERENCES `orders`(`order_id`),
    FOREIGN KEY (`user_coupon_id`) REFERENCES `user_coupon`(`user_coupon_id`)
);

CREATE TABLE `return_delivery` (
    `return_delivery_id` int NOT NULL AUTO_INCREMENT,
    `return_status` varchar(20) NULL,
    `courier` Date NULL,
    `invoice_number` int NULL,
    `tracking_delivery` varchar(255) NULL,
    `collection_complete_date` Date NULL,
    `delivery_fee` decimal NULL,
    `collection_status` varchar(20) NULL,
    `payment_status` int NULL,
    `return_id` int NOT NULL,
    `delivery_address_id` int NOT NULL,
    PRIMARY KEY (`return_delivery_id`),
    FOREIGN KEY (`return_id`) REFERENCES `returns`(`return_id`),
    FOREIGN KEY (`delivery_address_id`) REFERENCES `delivery_address`(`delivery_address_id`)
    );

select * from return_delivery;