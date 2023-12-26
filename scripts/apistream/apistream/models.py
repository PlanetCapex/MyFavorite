# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#   * Rearrange models' order
#   * Make sure each model has one field with primary_key=True
#   * Make sure each ForeignKey and OneToOneField has `on_delete` set to the desired behavior
#   * Remove `managed = False` lines if you wish to allow Django to create, modify, and delete the table
# Feel free to rename the models, but don't rename db_table values or field names.
from django.db import models


class Good(models.Model):
    id = models.BigIntegerField(primary_key=True)
    country = models.CharField(max_length=255, blank=True, null=True)
    description = models.CharField(max_length=255, blank=True, null=True)
    filename = models.CharField(max_length=255, blank=True, null=True)
    stash = models.CharField(max_length=255, blank=True, null=True)
    breed = models.CharField(max_length=255, blank=True, null=True)
    title = models.CharField(max_length=255, blank=True, null=True)
    producer = models.CharField(max_length=255, blank=True, null=True)
    price = models.IntegerField()
    type = models.CharField(max_length=255, blank=True, null=True)
    volume = models.CharField(max_length=255, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'good'


class Orders(models.Model):
    id = models.BigAutoField(primary_key=True)
    address = models.CharField(max_length=255, blank=True, null=True)
    city = models.CharField(max_length=255, blank=True, null=True)
    date = models.DateField(blank=True, null=True)
    email = models.CharField(max_length=255, blank=True, null=True)
    first_name = models.CharField(max_length=255, blank=True, null=True)
    last_name = models.CharField(max_length=255, blank=True, null=True)
    phone_number = models.CharField(max_length=255, blank=True, null=True)
    post_index = models.IntegerField()
    status = models.CharField(max_length=255, blank=True, null=True)
    total_price = models.FloatField(blank=True, null=True)
    user = models.ForeignKey('Usr', models.DO_NOTHING, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'orders'


class OrdersGoodList(models.Model):
    order = models.OneToOneField(Orders, models.DO_NOTHING, primary_key=True)
    good_list = models.ForeignKey(Good, models.DO_NOTHING)
    good_list_order = models.IntegerField()

    class Meta:
        managed = False
        db_table = 'orders_good_list'
        unique_together = (('order', 'good_list_order'),)


class UserRole(models.Model):
    user = models.ForeignKey('Usr', models.DO_NOTHING)
    roles = models.CharField(max_length=255, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'user_role'


class Usr(models.Model):
    id = models.BigIntegerField(primary_key=True)
    activation_code = models.CharField(max_length=255, blank=True, null=True)
    active = models.BooleanField()
    email = models.CharField(max_length=255, blank=True, null=True)
    password = models.CharField(max_length=255)
    username = models.CharField(max_length=255)

    class Meta:
        managed = False
        db_table = 'usr'


class UsrGoodList(models.Model):
    user = models.ForeignKey(Usr, models.DO_NOTHING)
    good_list = models.ForeignKey(Good, models.DO_NOTHING)

    class Meta:
        managed = False
        db_table = 'usr_good_list'
