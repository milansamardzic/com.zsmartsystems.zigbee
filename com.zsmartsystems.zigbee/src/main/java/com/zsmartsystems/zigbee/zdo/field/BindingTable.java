/**
 * Copyright (c) 2016-2017 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.zdo.field;

import com.zsmartsystems.zigbee.IeeeAddress;
import com.zsmartsystems.zigbee.serialization.ZigBeeDeserializer;
import com.zsmartsystems.zigbee.serialization.ZigBeeSerializer;
import com.zsmartsystems.zigbee.zcl.protocol.ZclDataType;

/**
 * Binding Table field.
 *
 * @author Chris Jackson
 */
public class BindingTable {
    /**
     * The source IEEE address for the binding entry.
     */
    private IeeeAddress srcAddr;

    /**
     * The source endpoint for the binding entry.
     */
    private int srcEndpoint;

    /**
     * The identifier of the cluster on the source device that is bound to the destination device.
     */
    private int clusterId;

    /**
     * Destination address mode
     * <p>
     * <ul>
     * <li>0x01 - Group address
     * <li>0x03 - IEEE address
     * </ul>
     */
    private int dstAddrMode;

    /**
     * Destination address if the address mode is group addressing
     */
    private int dstGroupAddr;

    /**
     * Destination address if the address mode is a node address
     */
    private IeeeAddress dstNodeAddr;

    /**
     * Destination endpoint if the address mode is a node address
     */
    private int dstNodeEndpoint;

    /**
     * @return the srcAddr
     */
    public IeeeAddress getSrcAddr() {
        return srcAddr;
    }

    /**
     * @return the srcEndpoint
     */
    public int getSrcEndpoint() {
        return srcEndpoint;
    }

    /**
     * @return the clusterId
     */
    public int getClusterId() {
        return clusterId;
    }

    /**
     * @return the dstAddrMode
     */
    public int getDstAddrMode() {
        return dstAddrMode;
    }

    /**
     * @return the dstGroupAddr
     */
    public int getDstGroupAddr() {
        return dstGroupAddr;
    }

    /**
     * @return the dstNodeAddr
     */
    public IeeeAddress getDstNodeAddr() {
        return dstNodeAddr;
    }

    /**
     * @return the dstNodeEndpoint
     */
    public int getDstNodeEndpoint() {
        return dstNodeEndpoint;
    }

    public void serialize(final ZigBeeSerializer serializer) {
        serializer.appendZigBeeType(srcAddr, ZclDataType.IEEE_ADDRESS);
        serializer.appendZigBeeType(srcEndpoint, ZclDataType.UNSIGNED_8_BIT_INTEGER);
        serializer.appendZigBeeType(clusterId, ZclDataType.CLUSTERID);
        serializer.appendZigBeeType(dstAddrMode, ZclDataType.UNSIGNED_8_BIT_INTEGER);
        if (dstAddrMode == 1) {
            serializer.appendZigBeeType(dstGroupAddr, ZclDataType.UNSIGNED_16_BIT_INTEGER);
        } else if (dstAddrMode == 3) {
            serializer.appendZigBeeType(dstNodeAddr, ZclDataType.IEEE_ADDRESS);
            serializer.appendZigBeeType(dstNodeEndpoint, ZclDataType.UNSIGNED_8_BIT_INTEGER);
        }
    }

    public void deserialize(final ZigBeeDeserializer deserializer) {
        srcAddr = (IeeeAddress) deserializer.readZigBeeType(ZclDataType.IEEE_ADDRESS);
        srcEndpoint = (int) deserializer.readZigBeeType(ZclDataType.UNSIGNED_8_BIT_INTEGER);
        clusterId = (int) deserializer.readZigBeeType(ZclDataType.CLUSTERID);
        dstAddrMode = (int) deserializer.readZigBeeType(ZclDataType.UNSIGNED_8_BIT_INTEGER);
        if (dstAddrMode == 1) {
            dstGroupAddr = (int) deserializer.readZigBeeType(ZclDataType.UNSIGNED_16_BIT_INTEGER);
        } else if (dstAddrMode == 3) {
            dstNodeAddr = (IeeeAddress) deserializer.readZigBeeType(ZclDataType.IEEE_ADDRESS);
            dstNodeEndpoint = (int) deserializer.readZigBeeType(ZclDataType.UNSIGNED_8_BIT_INTEGER);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(120);
        builder.append("BindingTable [srcAddr=");
        builder.append(srcAddr);
        builder.append('/');
        builder.append(srcEndpoint);
        builder.append(", dstAddr=");
        switch (dstAddrMode) {
            case 1:
                builder.append(dstGroupAddr);
                break;
            case 3:
                builder.append(dstNodeAddr);
                builder.append('/');
                builder.append(dstNodeEndpoint);
                break;
            default:
                builder.append(", Unknown destination mode");
                break;
        }
        builder.append(", clusterId=");
        builder.append(clusterId);
        builder.append(']');
        return builder.toString();
    }
}