CREATE TABLE `waypoints`
(
    `waypoint_id` BIGINT       PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(255) NULL DEFAULT NULL,
    `height`      DOUBLE       NOT NULL
);

CREATE TABLE  `available_paths`
(
    `available_path_id` BIGINT       PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name`              VARCHAR(255) NULL DEFAULT NULL,
    `height`            DOUBLE       NOT NULL
);
CREATE TABLE `waypoints_available_paths`
(
    `available_path_id` BIGINT NOT NULL,
    `waypoint_id`       BIGINT NOT NULL,
    FOREIGN KEY (`available_path_id`) REFERENCES `available_paths` (`available_path_id`),
    FOREIGN KEY (`waypoint_id`) REFERENCES `waypoints` (`waypoint_id`)
);

CREATE TABLE `routes`
(
    `route_id`              BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `start_waypoint_id`     BIGINT NOT NULL,
    `end_available_path_id` BIGINT NOT NULL,
    `length`                DOUBLE NOT NULL,
     FOREIGN KEY (`end_available_path_id`) REFERENCES `available_paths` (`available_path_id`),
     FOREIGN KEY (`start_waypoint_id`) REFERENCES `waypoints` (`waypoint_id`)
);

CREATE TABLE `hikes`
(
    `hike_id`  BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `date`     DATE   NOT NULL,
    `duration` TIME   NOT NULL
);
CREATE TABLE `hikes_routes`
(
    `hike_id`  BIGINT NOT NULL,
    `route_id` BIGINT NOT NULL,
    FOREIGN KEY (`route_id`) REFERENCES `routes` (`route_id`),
    FOREIGN KEY (`hike_id`) REFERENCES `hikes` (`hike_id`)
);
