#
# Copyright (C) 2021 The Descendant Project
#
# SPDX-License-Identifier: Apache-2.0
#

DEVICE_PATH := device/xiaomi/alioth

# Inherit from sm8250-common
-include device/xiaomi/sm8250-common/BoardConfigCommon.mk
-include vendor/xiaomi/alioth/BoardConfigVendor.mk

# Assert
TARGET_OTA_ASSERT_DEVICE := alioth,aliothin

# Bluetooth
BOARD_BLUETOOTH_BDROID_BUILDCFG_INCLUDE_DIR := $(DEVICE_PATH)/bluetooth/include

# Init
TARGET_INIT_VENDOR_LIB := //$(DEVICE_PATH):libinit_alioth
TARGET_RECOVERY_DEVICE_MODULES := libinit_alioth

# Kernel
TARGET_KERNEL_SOURCE := kernel/xiaomi/alioth
TARGET_KERNEL_CONFIG := vendor/alioth_defconfig

#Manifest
DEVICE_MANIFEST_FILE += $(DEVICE_PATH)/manifest.xml



