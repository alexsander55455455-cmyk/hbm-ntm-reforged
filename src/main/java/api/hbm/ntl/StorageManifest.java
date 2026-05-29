package api.hbm.ntl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

@Deprecated
public class StorageManifest {

    public LinkedHashMap<Integer, MetaNode> itemMeta = new LinkedHashMap<>();

    public void writeStack(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return;

        int id = Item.getIdFromItem(stack.getItem());
        MetaNode meta = itemMeta.get(id);

        if (meta == null) {
            meta = new MetaNode();
            itemMeta.put(id, meta);
        }

        NBTNode nbt = meta.metaNBT.get(stack.getItemDamage());

        if (nbt == null) {
            nbt = new NBTNode();
            meta.metaNBT.put(stack.getItemDamage(), nbt);
        }

        NBTTagCompound compound = stack.hasTagCompound() ? stack.getTagCompound().copy() : null;
        long amount = nbt.nbtAmount.containsKey(compound) ? nbt.nbtAmount.get(compound) : 0;

        amount += stack.getCount();

        nbt.nbtAmount.put(compound, amount);
    }

    public List<StorageStack> getStacks(boolean sorted) {
        List<StorageStack> stacks = new ArrayList<>();

        for (Entry<Integer, MetaNode> itemNode : itemMeta.entrySet()) {
            Item item = Item.getItemById(itemNode.getKey());
            if (item == null) continue;

            for (Entry<Integer, NBTNode> metaNode : itemNode.getValue().metaNBT.entrySet()) {
                for (Entry<NBTTagCompound, Long> nbtNode : metaNode.getValue().nbtAmount.entrySet()) {
                    ItemStack itemStack = new ItemStack(item, 1, metaNode.getKey());
                    if (nbtNode.getKey() != null) {
                        itemStack.setTagCompound(nbtNode.getKey().copy());
                    }
                    StorageStack stack = new StorageStack(itemStack, nbtNode.getValue());
                    stacks.add(stack);
                }
            }
        }

        if (sorted) Collections.sort(stacks);

        return stacks;
    }

    public class MetaNode {
        public LinkedHashMap<Integer, NBTNode> metaNBT = new LinkedHashMap<>();
    }

    public class NBTNode {
        public LinkedHashMap<NBTTagCompound, Long> nbtAmount = new LinkedHashMap<>();
    }
}
