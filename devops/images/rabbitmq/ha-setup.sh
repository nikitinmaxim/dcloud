#!/bin/sh

rabbitmqctl set_policy $HA_NAME "" \'\{$HA_MODE,$HA_SYNC\}\'
