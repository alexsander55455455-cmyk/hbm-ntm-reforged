package api.hbm.ntl;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

@Deprecated
public class StorageStack implements Comparable<StorageStack> {

    private final int cachedItemId;
    private final ItemStack type;
    private final long amount;

    public StorageStack(ItemStack type) {
        this(type, type == null || type.isEmpty() ? 0 : type.getCount());
    }

    public StorageStack(ItemStack type, long amount) {
        this.type = type == null ? ItemStack.EMPTY : type.copy();
        this.amount = amount;
        this.cachedItemId = this.type.isEmpty() ? 0 : Item.getIdFromItem(this.type.getItem());
        if (!this.type.isEmpty()) {
            this.type.setCount(0);
        }
    }

    public ItemStack getType() {
        return this.type.copy();
    }

    public long getAmount() {
        return this.amount;
    }

    @Override
    public int compareTo(StorageStack other) {
        if (other == null) return 1;

        if (this.cachedItemId < other.cachedItemId) return -1;
        if (this.cachedItemId > other.cachedItemId) return 1;
        if (this.type.getItemDamage() < other.type.getItemDamage()) return -1;
        if (this.type.getItemDamage() > other.type.getItemDamage()) return 1;

        NBTTagCompound thisTag = this.type.getTagCompound();
        NBTTagCompound otherTag = other.type.getTagCompound();
        if (thisTag != null && otherTag == null) return -1;
        if (thisTag == null && otherTag != null) return 1;
        if (thisTag != null) {
            int thisKeys = thisTag.getKeySet().size();
            int otherKeys = otherTag.getKeySet().size();
            if (thisKeys < otherKeys) return -1;
            if (thisKeys > otherKeys) return 1;
            int comp = thisTag.toString().compareTo(otherTag.toString());
            if (comp != 0) return comp;
        }

        if (this.type.getCount() < other.type.getCount()) return -1;
        if (this.type.getCount() > other.type.getCount()) return 1;
        return 0;
    }
}
